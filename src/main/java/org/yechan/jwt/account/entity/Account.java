package org.yechan.jwt.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    private String phone;
    
    private LocalDateTime createdDateTime;
    
    private LocalDateTime modifiedDateTime;
    
    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "role_type")})
    private Set<Authority> authorities=new HashSet<>();
}
