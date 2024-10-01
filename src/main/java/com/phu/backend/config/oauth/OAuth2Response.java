package com.phu.backend.config.oauth;

public interface OAuth2Response {
    // 소셜 제공자
    String getProvider();
    String getProviderId();
    // 이메일
    String getEmail();
    // 실명
    String getName();
}
