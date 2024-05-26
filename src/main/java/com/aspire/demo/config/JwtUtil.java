package com.aspire.demo.config;

import com.aspire.demo.model.User;
import com.aspire.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {

    @Autowired
    UserService userService;

    private static final String SECRET_KEY = "sFiBdK4L/KdKJ9yZUk9YZzELFZTjFe9o2ZZVCEm3HXM=";
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User validateUser (String authorizationHeader){
        if(authorizationHeader == null || authorizationHeader.length() < 8 ){
            throw new RuntimeException("wrong jwt token");
        }
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        if (JwtUtil.validateToken(token)) {
            String userId = JwtUtil.extractUsername(token);
            return userService.loadUserByUserId(userId);
        } else {
            // Handle invalid or expired token
            throw new RuntimeException("user not found");
        }
    }
}
