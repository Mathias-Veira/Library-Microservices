package com.library.user.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class SecurityConfig {
    private final JWTService jwtService;
    private final HandlerExceptionResolver resolver;
    public SecurityConfig(JWTService jwtService,@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver){
        this.jwtService = jwtService;
        this.resolver = resolver;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        JWTFilter jwtFilter = new JWTFilter(jwtService,resolver);
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth->auth.requestMatchers("/api/user/login","api/user/register","api/user/refresh").permitAll().anyRequest().authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
