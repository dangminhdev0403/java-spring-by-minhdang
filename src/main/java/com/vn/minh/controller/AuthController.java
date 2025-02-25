package com.vn.minh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.minh.domain.request.LoginReq;
import com.vn.minh.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationManagerBuilder authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<LoginReq> login(@RequestBody LoginReq loginReq) {

        String emailLogin = loginReq.getEmail();
        String passwordLogin = loginReq.getPassword();

        //! nạp vào  Spring Security để tái sự dụng thông tin người đăng nhập
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailLogin,
                passwordLogin);

        Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);
        return ResponseEntity.ok().body(loginReq);
    }

}
