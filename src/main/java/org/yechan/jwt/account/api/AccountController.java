package org.yechan.jwt.account.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.account.dto.AccountDto;
import org.yechan.jwt.account.service.AccountService;
import org.yechan.jwt.account.entity.Account;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Account> signup(
            @Validated @RequestBody AccountDto accountDto
    ) {
        return ResponseEntity.ok(accountService.signup(accountDto));
    }
    
    @GetMapping("/info")
    @PreAuthorize("USER")
    public ResponseEntity<Account> getMyInformation() {
        Account myInformation = accountService.getMyInformation();
        return ResponseEntity.ok(myInformation);
    }
}