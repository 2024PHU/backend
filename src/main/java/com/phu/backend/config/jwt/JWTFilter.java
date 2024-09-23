package com.phu.backend.config.jwt;

import com.phu.backend.dto.auth.MemberDetails;
import com.phu.backend.exception.jwt.AccessTokenExpiredException;
import com.phu.backend.exception.jwt.TokenNotValidateException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 에서 Authorization 헤더 조회
        String authorization = request.getHeader("Authorization");

        // 권한이 필요없는 요청일경우 해당 필터 생략
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);

            return;
        }

        // 순수 토큰 추출
        String accessToken = authorization.split(" ")[1];


        // 토큰 검증
        try {
            jwtUtil.isExpired(accessToken);
        }catch (ExpiredJwtException e) {
            throw new AccessTokenExpiredException();
        }catch (Exception e) {
            throw new TokenNotValidateException();
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            throw new TokenNotValidateException();
        }

        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);


        MemberDetails memberDetails = new MemberDetails(username, "temppassword", role);

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}