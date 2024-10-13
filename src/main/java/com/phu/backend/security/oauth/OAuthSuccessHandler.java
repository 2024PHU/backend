package com.phu.backend.security.oauth;

import com.phu.backend.domain.jwt.RefreshToken;
import com.phu.backend.domain.member.Member;
import com.phu.backend.exception.member.NotFoundMemberException;
import com.phu.backend.repository.jwt.RefreshTokenRepository;
import com.phu.backend.repository.member.MemberRepository;
import com.phu.backend.security.util.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
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

    private final MemberRepository memberRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String email = customUserDetails.getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        Member member = memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);

        log.info("username:{}", username);
        // 추가 회원가입
        if (role.equals("ROLE_BEFORE_USER")) {
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = "{\"email\": \"" + member.getEmail() + "\", \"name\": \"" + member.getName() + "\", \"social_id\": \"" + username + "\"}";
            response.getWriter().write(jsonResponse);
        }
        // 소셜 로그인 진행 및 jwt 발급
        if (role.equals("ROLE_USER")) {
            String access = jwtUtil.createJwt("access", email, role);
            String refresh = jwtUtil.createJwt("refresh", email, role);

            RefreshToken refreshTokenForRedis = RefreshToken.builder()
                    .refreshToken(refresh)
                    .email(email)
                    .build();

            refreshTokenRepository.save(refreshTokenForRedis);

            ResponseCookie refreshCookie = createRefreshCookie("refresh", refresh);
            response.setHeader("Set-Cookie", refreshCookie.toString());
            response.setHeader("Authorization", "Bearer " + access);
            log.info("token:{}", access);
        }
    }

    private ResponseCookie createRefreshCookie(String key, String value) {
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .maxAge(24 * 60 * 60)
                .secure(true)
                .httpOnly(true)
                .domain("fitee.site")
                .path("/")
                .sameSite("None")
                .build();

        return cookie;
    }
}
