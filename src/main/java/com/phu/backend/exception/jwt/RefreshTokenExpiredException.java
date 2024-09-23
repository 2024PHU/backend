package com.phu.backend.exception.jwt;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RefreshTokenExpiredException extends GlobalException {
    public static final String MESSAGE = "만료된 리프레시 토큰입니다.";
    @Override
    public String getStatusCode() {
        return "J002";
    }
    public RefreshTokenExpiredException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}