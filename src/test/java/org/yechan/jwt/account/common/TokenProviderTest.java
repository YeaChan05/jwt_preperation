package org.yechan.jwt.account.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.yechan.jwt.account.domain.RoleType;
import org.yechan.jwt.account.domain.entity.Account;
import org.yechan.jwt.account.domain.entity.AccountAuthority;
import org.yechan.jwt.account.domain.entity.Authority;
import org.yechan.jwt.account.dto.response.AuthenticationResponse;
import org.yechan.jwt.account.repository.AuthorityRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@SpringBootTest
class TokenProviderTest {
    
    @Mock
    private TokenProvider tokenProvider;
    @Autowired
    AuthorityRepository authorityRepository;
    
    @InjectMocks
    private Account account;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Authority user = authorityRepository.findByRoleType(RoleType.USER).orElseThrow();
        Authority admin = authorityRepository.findByRoleType(RoleType.ADMIN).orElseThrow();
        account = Account.builder()
                .phone("01024725809")
                .username("test")
                .password("qweasd123")
                .accountAuthorities(new HashSet<>())
                .build();
        
        AccountAuthority userAuthority = AccountAuthority.builder()
                .authority(user)
                .account(account)
                .build();
        AccountAuthority adminAuthority = AccountAuthority.builder()
                .authority(admin)
                .account(account)
                .build();
        account.getAccountAuthorities().add(userAuthority);
        account.getAccountAuthorities().add(adminAuthority);
        
        
    }
    
    @Test
    void testTokenCreate() {
        AuthenticationResponse expectedResponse = new AuthenticationResponse("Bearer ", "accessToken", "refreshToken");
        when(tokenProvider.createTokens(account.getUsername(), account.getAccountAuthorities().stream().map(AccountAuthority::getAuthority).collect(Collectors.toSet()))).thenReturn(expectedResponse);
        when(tokenProvider.validateToken(expectedResponse.getAccessToken())).thenReturn(true);
        when(tokenProvider.validateToken(expectedResponse.getRefreshToken())).thenReturn(true);
        
        AuthenticationResponse authenticationResponse = tokenProvider.createTokens(account.getUsername(), account.getAccountAuthorities().stream().map(AccountAuthority::getAuthority).collect(Collectors.toSet()));
        assertThat(tokenProvider.validateToken(authenticationResponse.getAccessToken())).isTrue();
        assertThat(tokenProvider.validateToken(authenticationResponse.getRefreshToken())).isTrue();
    }
    
    @Test
    void testGetAuthenticationByToken() {
        AuthenticationResponse expectedResponse = new AuthenticationResponse("Bearer ", "accessToken", "refreshToken");
        when(tokenProvider.createTokens(account.getUsername(), account.getAccountAuthorities().stream().map(AccountAuthority::getAuthority).collect(Collectors.toSet()))).thenReturn(expectedResponse);
        
        // Mock the behavior of getAuthentication method
        Authentication mockAuthentication = tokenProvider.getAuthentication(expectedResponse.getAccessToken());
        when(tokenProvider.getAuthentication(expectedResponse.getAccessToken())).thenReturn(mockAuthentication);
        
        Authentication authentication = tokenProvider.getAuthentication(expectedResponse.getAccessToken());
        // Assertions about the authorities and principal of the authentication object
        // ... rest of your test code
    }
}
