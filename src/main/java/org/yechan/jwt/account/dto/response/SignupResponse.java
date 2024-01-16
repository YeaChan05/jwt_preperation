package org.yechan.jwt.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class SignupResponse extends RepresentationModel<SignupResponse> {
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
