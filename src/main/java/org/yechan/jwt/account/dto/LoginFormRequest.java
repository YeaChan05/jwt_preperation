package org.yechan.jwt.account.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class LoginFormRequest {
    private final String username;
    private final String password;
}
