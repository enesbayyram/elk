package com.enesbayram.elk.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ElasticsearchLogger extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger("API_LOG");

    private static final int MAX_BODY_LENGTH = 5000;
    private static final Set<String> SENSITIVE_HEADERS = Set.of("authorization", "cookie", "set-cookie", "x-api-key");
    private static final List<String> SKIP_PREFIXES = List.of("/actuator", "/favicon.ico");

    final int cacheLimit = 1024 * 1024;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(req, MAX_BODY_LENGTH);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(res);

        String traceId = Optional.ofNullable(req.getHeader("X-Trace-Id")).orElse(UUID.randomUUID().toString());
        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            createLogModel(request, response, duration, traceId);
            response.copyBodyToResponse();
        }
    }

    private void createLogModel(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration, String traceId) {
        int status = response.getStatus();

        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("traceId", traceId);
        fields.put("method", request.getMethod());
        fields.put("endpoint", request.getRequestURI());
        fields.put("queryString", request.getQueryString());
        fields.put("clientIp", resolveClientIp(request));
        fields.put("requestHeaders", maskedHeaders(request));
        fields.put("requestBody", body(request.getContentAsByteArray()));
        fields.put("status", status);
        fields.put("responseBody", body(response.getContentAsByteArray()));
        fields.put("durationMs", duration);

        String msg = request.getMethod() + " " + request.getRequestURI() + " -> " + status + " (" + duration + " ms)";
        triggerAppender(status, fields, msg);
    }

    private void triggerAppender(Integer status, Map<String, Object> fields, String msg) {
        if (status >= 500) {
            log.error(Markers.appendEntries(fields), msg);
        } else if (status >= 400) {
            log.warn(Markers.appendEntries(fields), msg);
        } else {
            log.info(Markers.appendEntries(fields), msg);
        }
    }

    private Map<String, String> maskedHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Collections.list(request.getHeaderNames()).forEach(name -> headers.put(name, SENSITIVE_HEADERS.contains(name.toLowerCase()) ? "***" : request.getHeader(name)));
        return headers;
    }

    private String body(byte[] content) {
        if (content == null || content.length == 0) return null;
        String text = new String(content, StandardCharsets.UTF_8);
        return text.length() > MAX_BODY_LENGTH
                ? text.substring(0, MAX_BODY_LENGTH) + "...[truncated]"
                : text;
    }

    private String resolveClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        return (xff != null && !xff.isBlank()) ? xff.split(",")[0].trim() : request.getRemoteAddr();
    }
}
