package org.yechan.jwt.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yechan.jwt.account.domain.entity.Authority;
import org.yechan.jwt.account.domain.RoleType;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Optional<Authority> findByRoleType(RoleType roleType);
}
