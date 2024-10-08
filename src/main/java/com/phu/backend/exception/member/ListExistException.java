package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class ListExistException extends GlobalException {
    public static final String MESSAGE = "이미 리스트에 존재하는 회원입니다.";
    @Override
    public String getStatusCode() {
        return "M005";
    }
    public ListExistException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
