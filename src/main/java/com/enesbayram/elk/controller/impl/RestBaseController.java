package com.enesbayram.elk.controller.impl;

import com.enesbayram.elk.base.RestRootEntity;

public class RestBaseController {

    public <T> RestRootEntity<T> ok(T data){
        return RestRootEntity.ok(data);
    }
}
