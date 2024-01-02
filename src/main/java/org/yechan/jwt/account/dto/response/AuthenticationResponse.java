package org.yechan.jwt.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}