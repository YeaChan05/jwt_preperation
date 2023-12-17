package org.yechan.jwt.account;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginBody implements Serializable {
    private final String username;
    private final String password;
}
