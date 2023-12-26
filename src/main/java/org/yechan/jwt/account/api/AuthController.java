package org.yechan.jwt.account.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.account.service.AuthService;
import org.yechan.jwt.account.dto.LoginFormRequest;
import org.yechan.jwt.account.dto.AuthenticationResponse;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginFormRequest loginFormRequest) {
        AuthenticationResponse authenticationResponse = authService.login(loginFormRequest);
        String accessToken = authenticationResponse.getAccessToken();
        String refreshToken = authenticationResponse.getRefreshToken();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshToken)
                .body(accessToken);
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.SET_COOKIE) String refreshToken) {
        try {
            authService.logout(refreshToken);
            return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader(HttpHeaders.SET_COOKIE) String refreshToken) {
        try {
            String newAccessToken = authService.refresh(refreshToken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newAccessToken);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}