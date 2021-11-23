package com.article.article.config;

import java.util.Base64;

import com.article.article.security.JwtTokenFilter;
import com.article.article.security.JwtTokenProvider;
import com.article.article.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityContext {

    @Bean
    public JwtTokenProvider jwtTokenProvider(UserDetailsServiceImpl userDetailsService,
                                             @Value("${jwtSecret}") String jwtSecret,
                                             @Value("${jwtExpirationInMs}") Long jwtExpirationInMs) {
        var secret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        return new JwtTokenProvider(userDetailsService, secret, jwtExpirationInMs);
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(JwtTokenProvider tokenProvider, UserDetailsServiceImpl userDetailsService) {
        return new JwtTokenFilter(tokenProvider, userDetailsService);
    }

}
