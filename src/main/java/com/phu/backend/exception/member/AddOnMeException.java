package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class AddOnMeException extends GlobalException {
    public static final String MESSAGE = "자기 자신을 추가할 수 없습니다.";
    @Override
    public String getStatusCode() {
        return "M006";
    }
    public AddOnMeException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
