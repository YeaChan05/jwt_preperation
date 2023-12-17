package org.yechan.jwt.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final StringRedisTemplate redisTemplate;
    
    @Value("${jwt.access-token-expiration-seconds}")
    private Long accessTokenExpiry;
    
    @Value("${jwt.refresh-token-expiration-seconds}")
    private Long refreshTokenExpiry;
    
    public void blacklistToken(String accessToken, String refreshToken) {
        redisTemplate.opsForValue().set(accessToken, "blacklisted_access_token", accessTokenExpiry, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(refreshToken, "blacklisted_refresh_token", refreshTokenExpiry, TimeUnit.SECONDS);
    }
    
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
