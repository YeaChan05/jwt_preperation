package org.yechan.jwt.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.token.TokenBlacklistService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@Valid @RequestBody LoginBody loginBody) {
        TokenInfo tokenInfo =authService.login(loginBody);
        String grantType = tokenInfo.getGrantType();
        String accessToken = tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getRefreshToken();
        return ResponseEntity.status(HttpStatus.OK)
                .header("grantType",grantType)
                .header("accessToken",accessToken)
                .header("refreshToken",refreshToken).build();
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("accessToken") String accessToken,
                                    @RequestHeader("refreshToken") String refreshToken) {
        tokenBlacklistService.blacklistToken(accessToken,refreshToken);
        return ResponseEntity.ok().build();
    }
}