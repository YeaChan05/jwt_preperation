package org.yechan.jwt.account.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public class SignupResponse {
    private String username;
    private Set<String> authorities;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    
    
    @Override
    public String toString() {
        return "SignupResponse{" +
                "username='" + username + '\'' +
                ", authorities=" + authorities +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
