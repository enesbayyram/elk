package com.enesbayram.elk.handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError <T> {

    private Integer status;

    private String path;

    private String hostName;

    private Date errorTime;

    private T message;
}
