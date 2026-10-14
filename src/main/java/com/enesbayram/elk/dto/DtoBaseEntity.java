package com.enesbayram.elk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoBaseEntity {

    private String id;

    private LocalDateTime createTime;

    private String createUser;

    private LocalDateTime updateTime;

    private String updateUser;
}
