package com.phu.backend.exception.jwt;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class AccessTokenExpiredException extends GlobalException {
    public static final String MESSAGE = "만료된 엑세스 토큰입니다.";
    @Override
    public String getStatusCode() {
        return "J001";
    }
    public AccessTokenExpiredException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}