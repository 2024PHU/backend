package com.phu.backend.exception.dailychart;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotConnectedToTrainerException extends GlobalException {
    public static final String MESSAGE = "해당회원은 피티를 받고있는 회원이 아닙니다.";
    @Override
    public String getStatusCode() {
        return "D004";
    }
    public NotConnectedToTrainerException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
