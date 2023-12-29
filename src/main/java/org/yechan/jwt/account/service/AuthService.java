package org.yechan.jwt.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yechan.jwt.account.dto.LoginFormRequest;
import org.yechan.jwt.account.dto.AuthenticationResponse;

import java.time.Duration;
import java.util.Collection;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final StringRedisTemplate redisTemplate;
    
    public AuthenticationResponse login(LoginFormRequest loginFormRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginFormRequest.getUsername(), loginFormRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AccountDetails principal = (AccountDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return tokenProvider.createTokens(principal.getUsername(), authorities);
    }
    @Transactional
    public void logout(String refreshToken) throws IllegalAccessException {
        if (!tokenProvider.validateToken(refreshToken)) {//만료된 토큰인 경우
            log.info("토큰이 부적절합니다!");
            throw new IllegalAccessException("is invalid token");
        }
        
        Long leftSeconds = tokenProvider.getExpiredLeftSeconds(refreshToken);
        log.info("토큰 만료까지 남은 시간: {}", leftSeconds.toString());
        redisTemplate.opsForValue().set(refreshToken, "banned_token", Duration.ofSeconds(leftSeconds));
    }
    
    public String refresh(String refreshToken) throws IllegalAccessException {
        if (!tokenProvider.validateToken(refreshToken)) //만료된 토큰인 경우
            throw new IllegalAccessException("is invalid token");
        if(redisTemplate.opsForValue().get(refreshToken)!=null)
            throw new IllegalAccessError("is banned token");
        return tokenProvider.createNewAccessToken(refreshToken);
    }
}