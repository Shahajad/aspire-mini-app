package com.aspire.demo.util;

import com.aspire.demo.annotations.CommandsComponent;
import com.aspire.demo.model.Role;
import com.aspire.demo.model.User;
import com.aspire.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;

@Component
public class CreateDummyUser {
    @Autowired
    UserService userService;

    @PostConstruct
    public void createDummyUsers(){
        User user = User.builder()
                .id("admin")
                .username("admin")
                .password("admin")
                .roles(new HashSet<>(Collections.singletonList(Role.ADMIN)))
                .build();
        userService.registerUser(user);

        User user1 = User.builder()
                .id("user1")
                .username("user1")
                .password("user1")
                .roles(new HashSet<>(Collections.singletonList(Role.USER)))
                .build();
        userService.registerUser(user1);

        User user2 = User.builder()
                .id("user2")
                .username("user2")
                .password("user2")
                .roles(new HashSet<>(Collections.singletonList(Role.USER)))
                .build();
        userService.registerUser(user2);
    }
}
