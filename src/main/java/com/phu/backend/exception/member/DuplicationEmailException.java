package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class DuplicationEmailException extends GlobalException {
    public static final String MESSAGE = "중복된 이메일입니다.";
    @Override
    public String getStatusCode() {
        return "M001";
    }
    public DuplicationEmailException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
