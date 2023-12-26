package org.yechan.jwt.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yechan.jwt.account.dto.AccountDto;
import org.yechan.jwt.account.entity.RoleType;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.entity.Authority;
import org.yechan.jwt.account.repository.AccountRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Account signup(AccountDto accountDto) {
        if (accountRepository.findOneWithAuthoritiesByUsername(accountDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .roleType(RoleType.USER)
                .build();

        // 유저 정보를 만들어서 save
        Account account=Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .phone(accountDto.getPhone())
                .createdDateTime(accountDto.getCreatedDateTime())
                .modifiedDateTime(accountDto.getModifiedDateTime())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return accountRepository.save(account);
    }
    
    public Account getMyInformation() {
        AccountDetails principal = (AccountDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getAccount().getUsername();
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElse(null);
    }
}
