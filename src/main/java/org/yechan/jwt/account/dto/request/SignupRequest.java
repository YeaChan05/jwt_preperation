package org.yechan.jwt.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "회원가입 요청 body")
public class SignupRequest {
    @NotBlank
            @Schema(description = "회원 이름(중복 불가)")
    String username;
    @NotBlank
    @Schema(description = "회원 비밀번호")
    String password;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    @Schema(description = "전화번호 - 02-123-1234, 010-1234-1234등의 형식")
    String phone;
}