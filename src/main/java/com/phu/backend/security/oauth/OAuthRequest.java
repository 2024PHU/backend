package com.phu.backend.security.oauth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthRequest {
    private String email;
    private String role;
    private String username;
    private String name;

    @Builder
    public OAuthRequest(String role, String username, String name, String email) {
        this.role = role;
        this.username = username;
        this.name = name;
        this.email = email;
    }
}
