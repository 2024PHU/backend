package com.phu.backend.service.auth;

import com.phu.backend.config.jwt.JWTUtil;
import com.phu.backend.domain.jwt.RefreshToken;
import com.phu.backend.exception.jwt.RefreshTokenExpiredException;
import com.phu.backend.exception.jwt.RefreshTokenNotExistException;
import com.phu.backend.exception.jwt.TokenNotValidateException;
import com.phu.backend.repository.jwt.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    public String reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw new RefreshTokenNotExistException();
        }

        // 만료여부 검사
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new TokenNotValidateException();
        }

        // Redis 내부에 토큰이 있는지 검증
        RefreshToken redisToken = refreshTokenRepository.findById(refresh)
                .orElseThrow(RefreshTokenNotExistException::new);

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 재할당
        String accessToken = jwtUtil.createJwt("access", username, role);
        String refreshToken = jwtUtil.createJwt("refresh", username, role);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .email(username)
                .refreshToken(refreshToken)
                .build();

        refreshTokenRepository.delete(redisToken);
        refreshTokenRepository.save(newRefreshToken);

        response.addCookie(createCookie("refresh", refreshToken));

        return accessToken;
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setSecure(true); HTTPS 통신시 주석 해제
//        cookie.setPath("/"); 쿠키가 적용 될 범위 설정 필요시 사용
        cookie.setHttpOnly(true);

        return cookie;
    }
}
