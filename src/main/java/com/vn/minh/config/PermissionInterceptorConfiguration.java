package com.vn.minh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.minh.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Bean
    PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor(userService, objectMapper);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] whiteList = { "/**", "/admin/**" };
        registry.addInterceptor(getPermissionInterceptor()).addPathPatterns(whiteList);
    }
}
