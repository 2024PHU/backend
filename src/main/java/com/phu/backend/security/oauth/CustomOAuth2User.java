package com.phu.backend.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuthRequest oAuthRequest;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Map<String, Object> getAttribute(String name) {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return oAuthRequest.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oAuthRequest.getName();
    }

    public String getUsername() {
        return oAuthRequest.getUsername();
    }

    public String getEmail() {
        return oAuthRequest.getEmail();
    }
}
