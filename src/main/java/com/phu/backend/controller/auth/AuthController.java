package com.phu.backend.controller.auth;

import com.phu.backend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증 관련 API", description = "JWT 기반의 인증을 관리하는 API")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/reissue")
    @Operation(summary = "리프레시 토큰 발급", description = "클라이언트 쿠키에 있는 리프레시 토큰을 검증해 새로운 엑세스토큰을 재발급하는 API")
    public ResponseEntity<Void> reissue(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.reissue(request, response))
                .build();
    }


}
