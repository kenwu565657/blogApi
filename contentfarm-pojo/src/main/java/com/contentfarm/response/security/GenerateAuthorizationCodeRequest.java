package com.contentfarm.response.security;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GenerateAuthorizationCodeRequest {
    private String clientId;
    private Set<String> scope;
    private String username;
    private String password;
}
