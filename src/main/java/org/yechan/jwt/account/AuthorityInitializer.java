package org.yechan.jwt.account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yechan.jwt.account.entity.Authority;

import java.util.Arrays;

@Component
public class AuthorityInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    public AuthorityInitializer(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            Authority authority = Authority.builder()
                                    .roleType(roleType)
                                    .build();
            authorityRepository.save(authority);
        });
    }
}