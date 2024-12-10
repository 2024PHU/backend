package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class MemberRoleException extends GlobalException {
    public static final String MESSAGE = "해당 기능은 회원만 가능합니다.";
    @Override
    public String getStatusCode() {
        return "M008";
    }
    public MemberRoleException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
