package org.yechan.jwt.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GivenToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}