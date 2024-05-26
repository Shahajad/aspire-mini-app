package com.aspire.demo;

import com.aspire.demo.controller.AuthController;
import com.aspire.demo.model.AuthRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.message.callback.SecretKeyCallback;

@SpringBootTest
class AspireBackendApplicationTests {

	@Autowired
	AuthController authController;

	@Test
	void contextLoads() {
	}

}
