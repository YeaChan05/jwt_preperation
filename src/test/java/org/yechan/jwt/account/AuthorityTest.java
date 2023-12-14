package org.yechan.jwt.account;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorityTest {
    @Test
    void testAuthority() {
        Authority authority = new Authority(RoleType.USER);
        assertThat(authority.getAuthority()).isEqualTo("USER");
    }
}