package org.yechan.jwt.account.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.yechan.jwt.account.domain.entity.Account;
import org.yechan.jwt.account.domain.entity.AccountAuthority;
import org.yechan.jwt.account.domain.entity.Authority;
import org.yechan.jwt.account.domain.RoleType;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    
    Account account;
    
    @BeforeEach
    @Transactional
    void setUp() {
        authorityRepository.save(new Authority(RoleType.USER));
        authorityRepository.save(new Authority(RoleType.ADMIN));
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
        accountRepository.save(account);
    }
    
    @Test
    void testFindOneWithAuthoritiesByUsername() {
        assertThat(accountRepository.findOneWithAuthoritiesByUsername("test").get().getAccountAuthorities()).isNotEmpty();
    }
}
