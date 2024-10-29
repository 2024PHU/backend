package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class MemberDuplicationException extends GlobalException {
    public static final String MESSAGE = "자기 자신을 추가할 순 없습니다.";
    @Override
    public String getStatusCode() {
        return "M007";
    }
    public MemberDuplicationException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
