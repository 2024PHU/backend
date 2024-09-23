package com.phu.backend.domain.jwt;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 1000000)
public class RefreshToken {
    @Id
    private String refreshToken;
    private String email;
    @Builder
    public RefreshToken(String refreshToken, String email) {
        this.refreshToken = refreshToken;
        this.email = email;
    }
}
