package com.raontec.carbookingapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TokenDto {
    private String userId;
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
}
