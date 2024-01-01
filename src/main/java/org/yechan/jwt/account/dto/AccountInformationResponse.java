package org.yechan.jwt.account.dto;

import lombok.Builder;
import lombok.Getter;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.entity.AccountAuthority;
import org.yechan.jwt.account.entity.Authority;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
public class AccountInformationResponse {
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String phone;
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
