package com.aspire.demo.service;

import com.aspire.demo.model.Role;
import com.aspire.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<String, User> userStore = new HashMap<>();
    public User registerUser(User user) {
        if (userStore.containsKey(user.getId())) {
            throw new RuntimeException("User already exists");
        }
        if(user.getId() == null || user.getId().isBlank()){
            String userId = UUID.randomUUID().toString();
            user.setId(userId);
        }
        userStore.put(user.getId(), user);

        return user;
    }

    public User loadUserByUserId(String userId) {
        User user = userStore.get(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

}
