package org.yechan.jwt.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.token.TokenProvider;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@Valid @RequestBody FormBody formBody) {
        TokenInfo tokenInfo=authService.login(formBody);
        return ResponseEntity.ok(tokenInfo);
    }
}