package com.aspire.demo.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
    private List<String> swaggerUrls = List.of("swagger-ui", "/v3/api-docs", "/swagger-resources", "swagger-ui.html");

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        // Permit specific requests
        if (isAllowedRequest(requestURI) || requestURI.startsWith("authenticate") ) {
            chain.doFilter(request, response);
            return;
        }
        // Other requests are authenticated by the parent filter
        super.doFilterInternal(request, response, chain);
    }
     private boolean isAllowedRequest(String requestURI) {
        for(String swaggerUrl : swaggerUrls){
            if (requestURI.contains(swaggerUrl)) return true;
        }
        return false;
    }
}
