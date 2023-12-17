package org.yechan.jwt.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.yechan.jwt.account.*;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.entity.AccountDetails;
import org.yechan.jwt.account.entity.Authority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TokenProviderTest {
    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    AccountDetailsService accountDetailsService;
    private Account account;

    @BeforeEach
    void setUp() {
        Authority user = new Authority(RoleType.USER);
        Authority admin = new Authority(RoleType.ADMIN);
        Set<Authority> authorities = Set.of(
                user,
                admin);

        account = Account.builder()
                .phone("01024725809")
                .createdDateTime(LocalDateTime.now())
                .username("test")
                .password("qweasd123")
                .authorities(authorities)
                .build();

        authorityRepository.saveAll(List.of(user,admin));
    }


    @Test
    void testTokenCreate() {
        GivenToken givenToken = tokenProvider.createToken(account.getUsername(), account.getAuthorities());
        assertThat(tokenProvider.validateToken(givenToken.getAccessToken())).isTrue();
        assertThat(tokenProvider.validateToken(givenToken.getRefreshToken())).isTrue();
    }

    @Test
    void testGetAuthenticationByToken() {
        accountRepository.save(account);//저장된 account를 가져와야 하기 때문에 저장해야함
        GivenToken givenToken = tokenProvider.createToken(account.getUsername(), account.getAuthorities());
        
        Authentication authentication = tokenProvider.getAuthentication(givenToken.getAccessToken());
        assertThat(authentication.getAuthorities().parallelStream()
                .map(grantedAuthority -> (Authority)grantedAuthority)
                .map(Authority::getAuthority))
                .contains("USER","ADMIN");

        AccountDetails principal = (AccountDetails) authentication.getPrincipal();
        assertThat(principal.getUsername()).isEqualTo("test");
        assertThat(principal.getPassword()).isEqualTo("qweasd123");//인코딩 안쪽 로직을 통했으므로 테스트가 통과된다.
        accountRepository.deleteAll();
    }
}