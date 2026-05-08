package com.library.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final JWTService jwtService;
    private final RateLimitService rateLimitService;

    public SecurityConfig(JWTService jwtService,RateLimitService rateLimitService) {
        this.jwtService = jwtService;
        this.rateLimitService = rateLimitService;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        JWTFilter jwtFilter = new JWTFilter(jwtService,rateLimitService);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable).authorizeExchange(exchanges -> exchanges.pathMatchers("/api/user/login","api/user/register","api/user/refresh").permitAll().anyExchange().permitAll()).securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }
}
