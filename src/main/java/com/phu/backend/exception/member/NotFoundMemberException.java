package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundMemberException extends GlobalException {
    public static final String MESSAGE = "회원을 찾지 못했습니다.";
    @Override
    public String getStatusCode() {
        return "M003";
    }
    public NotFoundMemberException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
