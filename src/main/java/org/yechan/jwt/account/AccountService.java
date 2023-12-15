package org.yechan.jwt.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final  AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Account signup(AccountDto accountDto) {
        if (accountRepository.findOneWithAuthoritiesByUsername(accountDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityId(RoleType.USER)
                .build();

        // 유저 정보를 만들어서 save
        Account account=Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return accountRepository.save(account);
    }

}
