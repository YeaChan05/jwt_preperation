package org.yechan.jwt.account.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.yechan.jwt.account.domain.BaseTimeEntity;

import java.util.Set;

@Entity
@Table(name = "account")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
public class Account extends BaseTimeEntity {
    @Id
    @Column(name = "account_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    private String phone;
    
    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;
    
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AccountAuthority> accountAuthorities;
    
    public void addAccountAuthorities(Set<AccountAuthority> accountAuthorities) {
        this.accountAuthorities = accountAuthorities;
        accountAuthorities.forEach(accountAuthority -> accountAuthority.setAccount(this));
    }
}
