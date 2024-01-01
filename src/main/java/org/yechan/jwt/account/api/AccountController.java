package org.yechan.jwt.account.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.account.dto.AccountInformationResponse;
import org.yechan.jwt.account.dto.SignupRequest;
import org.yechan.jwt.account.dto.SignupResponse;
import org.yechan.jwt.account.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Validated @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.ok(accountService.signup(signupRequest));
    }
    
    @GetMapping("/info")
    @PreAuthorize("USER")
    public ResponseEntity<AccountInformationResponse> getMyInformation() {
        AccountInformationResponse myInformation = accountService.getMyInformation();
        return ResponseEntity.ok(myInformation);
    }
}