package com.vn.minh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.minh.repository.PermissionRepository;
import com.vn.minh.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("null")

public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final PermissionRepository permissionRepository;

    @Bean
    PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor(userService, objectMapper, permissionRepository);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/**") // Áp dụng cho tất cả đường dẫn
                .excludePathPatterns("/public/**", "/login", "/error"); // Ngoại lệ
    }

}
