package org.yechan.jwt.account.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.yechan.jwt.account.entity.Account;
import org.yechan.jwt.account.entity.Authority;
import org.yechan.jwt.account.entity.RoleType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    
    Account account;
    @BeforeEach
    void setUp() {
        Authority user = new Authority(RoleType.USER);
        Authority admin = new Authority(RoleType.ADMIN);
        Set<Authority> authorities = Set.of(user, admin);
        
        account = Account.builder()
                .phone("01024725809")
                .username("test")
                .password("qweasd123")
                .authorities(authorities)
                .build();
        accountRepository.save(account);
    }
    
    @Test
    void testFindOneWithAuthoritiesByUsername() {
        assertThat(accountRepository.findOneWithAuthoritiesByUsername("test").get().getAuthorities()).isNotEmpty();
    }
}
