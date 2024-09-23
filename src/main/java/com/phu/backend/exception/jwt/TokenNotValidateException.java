package com.phu.backend.exception.jwt;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class TokenNotValidateException extends GlobalException {
    public static final String MESSAGE = "잘못된 JWT 토큰입니다.";
    @Override
    public String getStatusCode() {
        return "J004";
    }
    public TokenNotValidateException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}