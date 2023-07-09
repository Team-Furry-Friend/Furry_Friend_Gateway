package com.v3.furry_friend_gateway.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtResponse {

    private Long memberId;
    private String memberName;
}
