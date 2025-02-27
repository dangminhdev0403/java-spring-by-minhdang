package com.vn.minh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.minh.domain.User;
import com.vn.minh.domain.request.LoginReq;
import com.vn.minh.domain.response.ResLoginDTO;
import com.vn.minh.service.UserService;
import com.vn.minh.service.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final SecurityUtils securityUtils;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@RequestBody LoginReq loginReq) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginReq.getEmail(), loginReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        User currentUser = this.userService.findByUsername(loginReq.getEmail());

        ResLoginDTO resLoginDTO = new ResLoginDTO();

        if (currentUser != null) {

            String email = currentUser.getEmail();
            String name = currentUser.getName();

            ResLoginDTO.UserLogin userLogin = ResLoginDTO.UserLogin.builder().email(email).name(name).build();

            String accessToken = this.securityUtils.createAccessToken(email, userLogin);

            resLoginDTO.setAccessToken(accessToken);
            resLoginDTO.setUser(userLogin);

        }

        return ResponseEntity.ok().body(resLoginDTO);
    }
}
