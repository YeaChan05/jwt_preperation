package org.yechan.jwt.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yechan.jwt.account.domain.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    
    @Query("SELECT a FROM Account a " +
            "LEFT JOIN FETCH a.accountAuthorities aa " +
            "LEFT JOIN FETCH aa.authority " +
            "WHERE a.username = :username")
    Optional<Account> findOneWithAuthoritiesByUsername(@Param("username") String username);
}
