package org.yechan.jwt.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yechan.jwt.token.TokenProvider;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ChannelCandidateResponse> login(@Valid @RequestBody FormBody formBody) {
    }
}
