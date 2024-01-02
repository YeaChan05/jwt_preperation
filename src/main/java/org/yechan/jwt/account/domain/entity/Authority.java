package org.yechan.jwt.account.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.yechan.jwt.account.domain.RoleType;

import java.util.Set;

@Entity
@Table(name = "authority")
@NoArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @Column(name = "authority_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", length = 50)
    private RoleType roleType;
    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AccountAuthority> accountAuthorities ;

    @Builder
    public Authority(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String getAuthority() {
        return roleType.name();
    }

}