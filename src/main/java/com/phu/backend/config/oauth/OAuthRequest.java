package com.phu.backend.config.oauth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthRequest {
    private String role;
    private String username;
    private String name;

    @Builder
    public OAuthRequest(String role, String username, String name) {
        this.role = role;
        this.username = username;
        this.name = name;
    }
}
