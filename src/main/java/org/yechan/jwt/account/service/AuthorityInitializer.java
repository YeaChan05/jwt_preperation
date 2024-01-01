package org.yechan.jwt.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yechan.jwt.account.entity.Authority;
import org.yechan.jwt.account.entity.RoleType;
import org.yechan.jwt.account.repository.AuthorityRepository;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorityInitializer {
    private final AuthorityRepository authorityRepository;
    
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeAuthority(){
        Set<Authority> authorities = Arrays.stream(RoleType.values())
                .filter(roleType -> authorityRepository.findByRoleType(roleType).isEmpty())
                .map(AuthorityInitializer::authorityNameOf)
                .collect(Collectors.toSet());
        authorityRepository.saveAll(authorities);
    }
    
    private static Authority authorityNameOf(RoleType roleType) {
        return Authority.builder()
                .roleType(roleType)
                .build();
    }
}
