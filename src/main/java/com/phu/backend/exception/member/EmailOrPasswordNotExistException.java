package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class EmailOrPasswordNotExistException extends GlobalException {
    public static final String MESSAGE = "데이터베이스 내부에 존재하지 않는 이메일 혹은 비밀번호입니다.";
    @Override
    public String getStatusCode() {
        return "M002";
    }
    public EmailOrPasswordNotExistException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}