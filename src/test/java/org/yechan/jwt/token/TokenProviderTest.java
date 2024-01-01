package org.yechan.jwt.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.yechan.jwt.account.dto.AuthenticationResponse;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.entity.Authority;
import org.yechan.jwt.account.entity.RoleType;
import org.yechan.jwt.account.service.TokenProvider;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TokenProviderTest {
    
    @Mock
    private TokenProvider tokenProvider;
    
    @InjectMocks
    private Account account;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Authority user = new Authority(RoleType.USER);
        Authority admin = new Authority(RoleType.ADMIN);
        Set<Authority> authorities = Set.of(user, admin);
        
        account = Account.builder()
                .phone("01024725809")
                .username("test")
                .password("qweasd123")
                .authorities(authorities)
                .build();
    }
    
    @Test
    void testTokenCreate() {
        AuthenticationResponse expectedResponse = new AuthenticationResponse("Bearer ", "accessToken", "refreshToken");
        when(tokenProvider.createTokens(account.getUsername(), account.getAuthorities())).thenReturn(expectedResponse);
        when(tokenProvider.validateToken(expectedResponse.getAccessToken())).thenReturn(true);
        when(tokenProvider.validateToken(expectedResponse.getRefreshToken())).thenReturn(true);
        
        AuthenticationResponse authenticationResponse = tokenProvider.createTokens(account.getUsername(), account.getAuthorities());
        assertThat(tokenProvider.validateToken(authenticationResponse.getAccessToken())).isTrue();
        assertThat(tokenProvider.validateToken(authenticationResponse.getRefreshToken())).isTrue();
    }
    
    @Test
    void testGetAuthenticationByToken() {
        AuthenticationResponse expectedResponse = new AuthenticationResponse("Bearer ", "accessToken", "refreshToken");
        when(tokenProvider.createTokens(account.getUsername(), account.getAuthorities())).thenReturn(expectedResponse);
        
        // Mock the behavior of getAuthentication method
        Authentication mockAuthentication = tokenProvider.getAuthentication(expectedResponse.getAccessToken());
        when(tokenProvider.getAuthentication(expectedResponse.getAccessToken())).thenReturn(mockAuthentication);
        
        Authentication authentication = tokenProvider.getAuthentication(expectedResponse.getAccessToken());
        // Assertions about the authorities and principal of the authentication object
        // ... rest of your test code
    }
}
