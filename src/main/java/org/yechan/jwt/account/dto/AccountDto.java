package org.yechan.jwt.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.yechan.jwt.account.entity.Account;

import java.time.LocalDateTime;

/**
 * DTO for {@link Account}
 */
@Value
@ToString
@Builder
@Jacksonized
public class AccountDto {
    String username;
    String password;
    String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime modifiedDateTime;
}