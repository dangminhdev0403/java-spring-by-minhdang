package com.vn.minh.domain.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseData<T> {
    private String error;
    private Object message;
    private int status;
    private T data;
}
