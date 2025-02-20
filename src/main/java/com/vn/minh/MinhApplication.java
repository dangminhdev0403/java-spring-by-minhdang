package com.vn.minh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class MinhApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhApplication.class, args);
	}

}
