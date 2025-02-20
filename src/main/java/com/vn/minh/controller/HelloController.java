package com.vn.minh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/user")
    public String getMethodName() {
        return "Hello world";
    }

}
