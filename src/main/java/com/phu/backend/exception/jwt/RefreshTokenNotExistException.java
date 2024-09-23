package com.phu.backend.exception.jwt;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotExistException extends GlobalException {
    public static final String MESSAGE = "리프레시 토큰 조회에 실패했습니다";
    @Override
    public String getStatusCode() {
        return "J003";
    }
    public RefreshTokenNotExistException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}