package org.yechan.jwt.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yechan.jwt.account.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, RoleType> {
}
