package org.yechan.jwt.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class LoginFormRequest {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
}
