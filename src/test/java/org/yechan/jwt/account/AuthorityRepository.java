package org.yechan.jwt.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, RoleType> {
}
