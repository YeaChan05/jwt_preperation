package org.yechan.jwt.account;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Account}
 */
@Value

public class AccountDto implements Serializable {
    String username;
    String password;
    String phone;
    LocalDateTime createdDateTime;
    LocalDateTime modifiedDateTime;
}