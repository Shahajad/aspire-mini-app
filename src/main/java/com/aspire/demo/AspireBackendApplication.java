package com.aspire.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class AspireBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AspireBackendApplication.class, args);
	}

}
