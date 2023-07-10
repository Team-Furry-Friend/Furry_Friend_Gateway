package com.v3.furry_friend_gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.v3.furry_friend_gateway.service.dto.response.JwtResponse;

@Component
@Log4j2
public class JwtService {

    private final String type = "Bearer ";

    @Value("${jwt.secretKey}")
    private String key;

    // 토큰의 유효성 검사 메서드
    public JwtResponse validateToken(String token) {
        try {
            Optional<Claims> result = parse(key, token);

            Long memberId = null;
            String name = null;

            if (result.isPresent()){
                Claims claims = result.get();
                memberId = claims.get("memberId", Long.class);
                name = claims.get("name", String.class);
            }else{
                throw new Exception("토큰 검증 실패");
            }

            return JwtResponse.builder()
                .memberId(memberId)
                .memberName(name)
                .build();
        } catch (SignatureException e) {
            // 토큰이 유효하지 않으면 false를 반환
            log.error("SignatureException: " + e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 토큰의 유효성 검증
    public Optional<Claims> parse(String key, String token) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(untype(token)).getBody());
        } catch (JwtException e) {
            return Optional.empty();
        }
    }

    private String untype(String token) {
        return token.substring(type.length());
    }

}
