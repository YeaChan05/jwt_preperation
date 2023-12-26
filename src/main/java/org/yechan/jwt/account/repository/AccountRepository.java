package org.yechan.jwt.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yechan.jwt.account.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findOneWithAuthoritiesByUsername(String username);
}
