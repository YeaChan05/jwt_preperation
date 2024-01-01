package org.yechan.jwt.account.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.yechan.jwt.account.entity.Account;

/**
 * DTO for {@link Account}
 */
@Value
@ToString
@Builder
@Jacksonized
public class SignupRequest {
    String username;
    String password;
    String phone;
}