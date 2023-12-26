package org.yechan.jwt.account.dto;

import lombok.Value;
import org.yechan.jwt.account.entity.Account;

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