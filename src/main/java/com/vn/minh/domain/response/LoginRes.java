package com.vn.minh.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LoginRes {

    private long id;
    private String name;
    private String email;
}
