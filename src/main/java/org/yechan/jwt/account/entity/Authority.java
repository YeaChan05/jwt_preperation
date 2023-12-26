package org.yechan.jwt.account.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
@NoArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorityId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", length = 50)
    private RoleType roleType;

    @Builder
    public Authority(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String getAuthority() {
        return roleType.name();
    }

}