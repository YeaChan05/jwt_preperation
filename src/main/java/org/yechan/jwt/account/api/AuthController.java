package org.yechan.jwt.account.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.account.dto.response.AuthenticationResponse;
import org.yechan.jwt.account.dto.request.LoginFormRequest;
import org.yechan.jwt.account.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원 인증 및 인가를 다루는 서비스 api docs")
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    @Operation(summary = "로그인",description = "아이디와 비밀번호로 로그인 요청")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody
            LoginFormRequest loginFormRequest) {
        AuthenticationResponse authenticationResponse = authService.login(loginFormRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationResponse);
    }
    
    @GetMapping("/logout")
    @Operation(summary = "로그아웃",description = "로그아웃 및 클라이언트단 토큰 소멸")
    public ResponseEntity<?> logout(
            @RequestHeader("refreshToken")
            String refreshToken) {
        try {
            authService.logout(refreshToken);
            return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급",description = "refresh token을 전달하여 access token을 재발급")
    public ResponseEntity<?> refresh(
            @RequestBody
            String refreshToken) {
        try {
            String newAccessToken = authService.refresh(refreshToken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newAccessToken);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}