package com.phu.backend.exception.oauth;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundSocialIdException extends GlobalException {
    public static final String MESSAGE = "소셜아이디를 찾지 못했습니다.";
    @Override
    public String getStatusCode() {
        return "SO001";
    }
    public NotFoundSocialIdException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
