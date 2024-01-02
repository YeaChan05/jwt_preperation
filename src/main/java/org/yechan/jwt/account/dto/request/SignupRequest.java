package org.yechan.jwt.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.yechan.jwt.account.domain.entity.Account;

/**
 * DTO for {@link Account}
 */
@Value
@ToString
@Builder
@Jacksonized
public class SignupRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    String phone;
}