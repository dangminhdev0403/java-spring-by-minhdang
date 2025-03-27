package com.vn.minh.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.minh.domain.model.User;
import com.vn.minh.domain.request.LoginReq;
import com.vn.minh.domain.response.ResLoginDTO;
import com.vn.minh.service.UserService;
import com.vn.minh.service.util.SecurityUtils;
import com.vn.minh.service.util.error.MessageCustomExcetion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final SecurityUtils securityUtils;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${minh.jwt.refresh-token.validity.in.seconds}")
    private long refreshTokenExpiration;

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginReq loginReq ) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginReq.getEmail(), loginReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        User currentUser = this.userService.findByUsername(loginReq.getEmail());

        // Khởi tạo với giá trị mặc định
        ResLoginDTO resLoginDTO = ResLoginDTO.builder().build();
        ResLoginDTO.UserLogin userLogin = ResLoginDTO.UserLogin.builder().build();

        if (currentUser != null) {
            String email = currentUser.getEmail();
            String name = currentUser.getName();
            userLogin = ResLoginDTO.UserLogin.builder().email(email).name(name).build();
        }

        String accessToken = this.securityUtils.createAccessToken(loginReq.getEmail(), userLogin);
        resLoginDTO = ResLoginDTO.builder()
                .accessToken(accessToken)
                .user(userLogin)
                .build();

        // ! Nạp thông tin hoi vào SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // ! Tạo refresh_token
        String refresh_token = this.securityUtils.createRefreshToken(loginReq.getEmail(), resLoginDTO);
        this.userService.updateRefreshToken(loginReq.getEmail(), refresh_token);
        // ! Lưu refresh_token với cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .path("/")
                .maxAge(refreshTokenExpiration).build();
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(resLoginDTO);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken)
            throws MessageCustomExcetion {

        if (refreshToken == null || refreshToken.isEmpty()) {

            throw new MessageCustomExcetion("Không tìm thấy refresh token");

        }

        Jwt decodedToken = this.securityUtils.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();

        Optional<User> currentUser = this.userService.getUserByRefreshTokenAndEmail(User.class, email, refreshToken);

        if (!currentUser.isPresent()) {
            throw new MessageCustomExcetion("Không tìm thấy refresh token");
        }
        String name = currentUser.get().getName();

        ResLoginDTO.UserLogin userLogin = ResLoginDTO.UserLogin.builder().email(email).name(name).build();
        String access_token = this.securityUtils.createAccessToken(email, userLogin);
        ResLoginDTO resLoginDTO = ResLoginDTO.builder().user(userLogin).accessToken(access_token).build();

        String refresh_token = this.securityUtils.createRefreshToken(email, resLoginDTO);
        this.userService.updateRefreshToken(email, refresh_token);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .path("/")
                .maxAge(refreshTokenExpiration).build();
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(resLoginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> getMethodName() throws MessageCustomExcetion {
        String email = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (email.equals(""))
            throw new MessageCustomExcetion("Access Token is invalid");
        this.userService.updateRefreshToken(email, null);
        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0).build();
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(null);
    }

}
