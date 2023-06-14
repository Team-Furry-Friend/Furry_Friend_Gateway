package com.v3.furry_friend_gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.v3.furry_friend_gateway.service.dto.response.JwtResponse;

@Component
@Log4j2
public class JwtService {

    @Value("${jwt.secretKey}")
    private String key;

    // 토큰의 유효성 검사 메서드
    public JwtResponse validateToken(String token) {
        try {
            // accessToken 파싱 및 memberId 추출
            Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

            Object object = claims.get("memberId"); // memberId 추출
            JwtResponse jwtResponse = null;

            if (object instanceof Long) {
                jwtResponse = new JwtResponse((Long) object);
            } else if (object instanceof Integer) {
                jwtResponse = new JwtResponse(((Integer) object).longValue());
            } else {
                log.warn(claims.get("memberId"));
            }

            return jwtResponse;
        } catch (SignatureException e) {
            // 토큰이 유효하지 않으면 false를 반환
            log.error("SignatureException: " + e);
            return null;
        }
    }

}
