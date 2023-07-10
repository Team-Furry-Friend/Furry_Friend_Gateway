package com.v3.furry_friend_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.v3.furry_friend_gateway.common.ApiResponse;
import com.v3.furry_friend_gateway.service.JwtService;
import com.v3.furry_friend_gateway.service.dto.response.JwtResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/gateway")
public class GatewayContoller {

    private final JwtService jwtService;

    @GetMapping("/isvalid")
    public ApiResponse<JwtResponse> isValid(@RequestHeader(value = "Authorization") String accessToken) {

        // 유효성 검사
        try{

            // 토큰 검사 성공
            JwtResponse jwtResponse = jwtService.validateToken(accessToken);
            return ApiResponse.success("토큰 검증 완료", jwtResponse);
        }catch (NullPointerException e) {

            // 토큰 검사 실패
            return ApiResponse.fail(401, "토큰 검증에 실패했습니다.");
        }catch (Exception e){

            // 토큰 타임 아웃
            log.error("토큰 오류 발생: " + e.getMessage());
            return ApiResponse.fail(500, "토큰 오류가 발생했습니다.");
        }
    }
}
