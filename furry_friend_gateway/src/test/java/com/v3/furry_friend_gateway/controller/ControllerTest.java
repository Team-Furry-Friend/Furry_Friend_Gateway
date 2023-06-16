package com.v3.furry_friend_gateway.controller;

import com.v3.furry_friend_gateway.common.ApiResponse;
import com.v3.furry_friend_gateway.service.JwtService;
import com.v3.furry_friend_gateway.service.dto.response.JwtResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private GatewayContoller gatewayContoller;

    @Test
    public void testIsValid_WithValidToken_ReturnsSuccessResponse() {


        // given
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiaWF0IjoxNjg2NzI1Mzg1LCJleHAiOjE2ODY3MjcxODV9.ru7HaBY1juftQM3WDMh3E44gYRPumXbUpmxiHnGDjG4";

        // when
        ApiResponse<JwtResponse> response = gatewayContoller.isValid(validToken);

        // then
        assertEquals(response.getStatusCode(), 200);
        assertEquals("토큰 검증 완료", response.getMessage());
    }

    @Test
    public void testIsValid_WithInvalidToken_ReturnsFailResponse() {

        // given
        String invalidToken = "invalidAccessToken";

        // when
        ApiResponse<JwtResponse> response = gatewayContoller.isValid(invalidToken);

        // then
        assertEquals(401, response.getStatusCode());
        assertEquals("토큰 검증에 실패했습니다.", response.getMessage());
    }
}
