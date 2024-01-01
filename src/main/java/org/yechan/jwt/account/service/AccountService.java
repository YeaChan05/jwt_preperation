package org.yechan.jwt.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yechan.jwt.account.dto.AccountInformationResponse;
import org.yechan.jwt.account.dto.SignupRequest;
import org.yechan.jwt.account.dto.SignupResponse;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.entity.AccountAuthority;
import org.yechan.jwt.account.entity.Authority;
import org.yechan.jwt.account.entity.RoleType;
import org.yechan.jwt.account.repository.AccountRepository;
import org.yechan.jwt.account.repository.AuthorityRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if (accountRepository.findOneWithAuthoritiesByUsername(signupRequest.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Authority user = authorityRepository.findByRoleType(RoleType.USER).orElseThrow();
        
        // 유저 정보를 만들어서 save
        Account account = Account.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phone(signupRequest.getPhone())
                .accountAuthorities(new HashSet<>())
                .activated(true)
                .build();
        
        AccountAuthority accountAuthority = AccountAuthority.builder()
                .authority(user)
                .account(account)
                .build();
        account.getAccountAuthorities().add(accountAuthority);
        Account savedAccount = accountRepository.saveAndFlush(account);
        
        return SignupResponse.builder()
                .username(savedAccount.getUsername())
                .authorities(savedAccount.getAccountAuthorities().stream()
                        .map(AccountAuthority::getAuthority)
                        .map(Authority::getAuthority)
                        .collect(Collectors.toSet()))
                .createdDate(account.getCreatedDate())
                .modifiedDate(account.getModifiedDate())
                .build();
    }
    
    public AccountInformationResponse getMyInformation() {
        AccountDetails principal = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getAccount().getUsername();
        Account account = accountRepository.findOneWithAuthoritiesByUsername(username).orElseThrow();
        return AccountInformationResponse.of(account);
    }
}
