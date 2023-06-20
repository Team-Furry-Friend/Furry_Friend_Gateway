package com.v3.furry_friend_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class GatewaySecurityConfig {
    // Gateway에 대한 보안 구성을 담당하는 설정 클래스입니다.

    // SecurityWebFilterChain을 설정하여 Gateway의 보안 설정을 정의합니다.
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()
            .logout().disable()
            .authorizeExchange()
            .pathMatchers("/**").permitAll()
            .and()
            .build();
    }
}
