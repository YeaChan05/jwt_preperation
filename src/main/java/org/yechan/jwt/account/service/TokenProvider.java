package org.yechan.jwt.account.service;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yechan.jwt.account.dto.AuthenticationResponse;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secretKey}")
    private String secretKey;
    
    @Value("${jwt.access-token-expiration-seconds}")
    private Long accessTokenValidTime;
    
    @Value("${jwt.refresh-token-expiration-seconds}")
    private Long refreshTokenValidTime;

    private final AccountDetailsService accountDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public AuthenticationResponse createTokens(String userPk, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        Header<?> header = Jwts.header();

        header.put("prefix", TOKEN_PREFIX);
        
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        
        String refreshToken = Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    public Authentication getAuthentication(String token) {
        UserDetails userDetails = accountDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    public Long getExpiredLeftSeconds(String refreshToken) {
        long expirationTimeMillis = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getExpiration()
                .getTime();
        long currentTimeMillis = new Date().getTime();
        return (expirationTimeMillis - currentTimeMillis) / 1000;
    }
    
    public String createNewToken(String refreshToken) {
        String username = getUsername(refreshToken);
        Authentication authentication = getAuthentication(refreshToken);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return createTokens(username,authorities).getAccessToken();
    }
}
