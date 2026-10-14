package com.enesbayram.elk.handler;

import com.enesbayram.elk.handler.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.util.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError<?>> handleException(Exception exception, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(), request));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        Map<String, List<String>> errorMap = new HashMap<>();
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        for (ObjectError objError : allErrors) {
            String fieldName = ((FieldError) objError).getField();
            if (errorMap.containsKey(fieldName)) {
                errorMap.put(fieldName, addValueToList(objError.getDefaultMessage(), errorMap.get(fieldName)));
            } else {
                errorMap.put(fieldName, addValueToList(objError.getDefaultMessage(), new ArrayList<>()));
            }
        }
        return ResponseEntity.badRequest().body(createApiError(errorMap, request));
    }

    public List<String> addValueToList(String value, List<String> list) {
        list.add(value);
        return list;
    }

    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException e) {
            log.error("Hostname alinirken hata oluştu : " + e.getMessage());
        }
        return "";
    }

    public <T> ApiError<T> createApiError(T errorMessage, WebRequest webRequest) {
        ApiError<T> apiError = new ApiError<T>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setErrorTime(new Date());
        apiError.setPath(webRequest.getDescription(false).substring(4));
        apiError.setHostName(getHostName());
        apiError.setMessage(errorMessage);
        return apiError;
    }
}
