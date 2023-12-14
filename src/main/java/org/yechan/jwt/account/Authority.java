package org.yechan.jwt.account;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "authority_id", length = 50)
    private RoleType authorityId;

    @Builder
    public Authority(RoleType authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public String getAuthority() {
        return authorityId.name();
    }

}