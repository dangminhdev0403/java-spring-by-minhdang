package com.vn.minh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String getMethodName() {

        return "Hello world";
    }

}
