package org.yechan.jwt.account.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.yechan.jwt.account.domain.entity.Account;
import org.yechan.jwt.account.domain.entity.AccountAuthority;
import org.yechan.jwt.account.domain.entity.Authority;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Schema(description = "계정 단일 정보 응답")
public class AccountInformationResponse {
    @Schema(description = "회원 이름")
    private String username;
    @Schema(description = "생성 시간")
    private LocalDateTime createdDate;
    @Schema(description = "수정 시간")
    private LocalDateTime modifiedDate;
    @Schema(description = "전화번호")
    private String phone;
    @Schema(description = "권한 - 복수 소유 가능")
    private Set<String> authorities;
    
    public static AccountInformationResponse of(Account account){
        return AccountInformationResponse.builder()
                .username(account.getUsername())
                .phone(account.getPhone())
                .createdDate(account.getCreatedDate())
                .modifiedDate(account.getModifiedDate())
                .authorities(account.getAccountAuthorities().stream().map(AccountAuthority::getAuthority).map(Authority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
