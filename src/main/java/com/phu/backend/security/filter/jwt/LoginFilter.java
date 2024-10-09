package com.phu.backend.security.filter.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phu.backend.domain.jwt.RefreshToken;
import com.phu.backend.dto.auth.MemberDetails;
import com.phu.backend.dto.member.request.LoginRequest;
import com.phu.backend.exception.member.EmailOrPasswordNotExistException;
import com.phu.backend.repository.jwt.RefreshTokenRepository;
import com.phu.backend.security.util.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest memberLoginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        String username = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        // Email 추출
        String username = memberDetails.getUsername();

        // 권한 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String access = jwtUtil.createJwt("access", username, role);
        String refresh = jwtUtil.createJwt("refresh", username, role);

        RefreshToken refreshTokenForRedis = RefreshToken.builder()
                .refreshToken(refresh)
                .email(username)
                .build();

        refreshTokenRepository.save(refreshTokenForRedis);

        response.setHeader("Authorization", "Bearer " + access);
        ResponseCookie refreshCookie = createCookie("refresh", refresh);
        response.setHeader("Set-Cookie", refreshCookie.toString());
        response.setStatus(HttpStatus.OK.value());
    }

    private ResponseCookie createCookie(String key, String value) {
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .maxAge(24 * 60 * 60)
                .secure(true)
                .httpOnly(true)
                .domain("fitee.site")
                .domain("localhost")
                .path("/")
                .sameSite("None")
                .build();

        return cookie;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws ServletException {;
        throw new EmailOrPasswordNotExistException();
    }
}