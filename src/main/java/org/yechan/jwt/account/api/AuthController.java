package org.yechan.jwt.account.api;

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
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginFormRequest loginFormRequest) {
        AuthenticationResponse authenticationResponse = authService.login(loginFormRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationResponse);
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("refreshToken") String refreshToken) {
        try {
            authService.logout(refreshToken);
            return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody String refreshToken) {
        try {
            String newAccessToken = authService.refresh(refreshToken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newAccessToken);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}