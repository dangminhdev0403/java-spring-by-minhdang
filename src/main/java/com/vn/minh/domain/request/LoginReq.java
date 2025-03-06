package com.vn.minh.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "password không được để trống")
    private String password;

}
