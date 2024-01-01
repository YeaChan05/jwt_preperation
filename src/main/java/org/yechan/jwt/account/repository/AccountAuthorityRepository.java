package org.yechan.jwt.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yechan.jwt.account.entity.AccountAuthority;

public interface AccountAuthorityRepository extends JpaRepository<AccountAuthority, Long> {
}