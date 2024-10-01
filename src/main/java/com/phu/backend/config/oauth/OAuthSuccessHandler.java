package com.phu.backend.config.oauth;

import com.phu.backend.config.jwt.JWTUtil;
import com.phu.backend.domain.jwt.RefreshToken;
import com.phu.backend.repository.jwt.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        log.info("username:{}", username);
        // 추가 회원가입
        if (role.equals("ROLE_BEFORE_USER")) {
            response.addCookie(createSocialCookie("social_id", username));
            response.sendRedirect("http://localhost:5173/social/sign-up");
        }
        // 소셜 로그인 진행 및 jwt 발급
        if (role.equals("ROLE_USER")) {
            String access = jwtUtil.createJwt("access", username, role);
            String refresh = jwtUtil.createJwt("refresh", username, role);

            RefreshToken refreshTokenForRedis = RefreshToken.builder()
                    .refreshToken(refresh)
                    .email(username)
                    .build();

            refreshTokenRepository.save(refreshTokenForRedis);

            response.addCookie(createRefreshCookie("refresh", refresh));
            response.setHeader("Authorization", "Bearer " + access);
            response.sendRedirect("http://localhost:5173/");
        }
    }

    private Cookie createRefreshCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
//        cookie.setPath("/"); 쿠키가 적용 될 범위 설정 필요시 사용
        cookie.setHttpOnly(true);

        return cookie;
    }

    private Cookie createSocialCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(30 * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
