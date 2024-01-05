package org.yechan.jwt.account.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.account.dto.request.SignupRequest;
import org.yechan.jwt.account.dto.response.AccountInformationResponse;
import org.yechan.jwt.account.dto.response.SignupResponse;
import org.yechan.jwt.account.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Tag(name = "Account", description = "회원정보에 대한 CRUD docs")
public class AccountController {
    private final AccountService accountService;
    @PostMapping("/signup")
    @Operation(summary = "회원가입",description = "회원 가입을 위한 form 요청입니다.")
    public ResponseEntity<SignupResponse> signup(
            @Validated
            @RequestBody
            SignupRequest signupRequest
    ) {
        return ResponseEntity.ok(accountService.signup(signupRequest));
    }
    
    @GetMapping("/info")
    @PreAuthorize("USER")
    @Operation(summary = "단일 회원정보 조회 - 사용자")
    public ResponseEntity<AccountInformationResponse> getMyInformation() {
        AccountInformationResponse myInformation = accountService.getMyInformation();
        return ResponseEntity.ok(myInformation);
    }
}