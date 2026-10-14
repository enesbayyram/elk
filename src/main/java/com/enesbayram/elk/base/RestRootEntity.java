package com.enesbayram.elk.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestRootEntity<T> {

    private boolean status;

    private T data;

    public static <T> RestRootEntity<T> ok(T data) {
        RestRootEntity<T> rootEntity = new RestRootEntity();
        rootEntity.setData(data);
        rootEntity.setStatus(true);
        return rootEntity;
    }
}
