package org.yechan.jwt.account.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginFormRequest implements Serializable {
    private final String username;
    private final String password;
}
