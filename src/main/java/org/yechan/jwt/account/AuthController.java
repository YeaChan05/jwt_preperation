package org.yechan.jwt.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<GivenToken> login(@Valid @RequestBody LoginBody loginBody) {
        GivenToken givenToken =authService.login(loginBody);
        return ResponseEntity.ok(givenToken);
    }
}