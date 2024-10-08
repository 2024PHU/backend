package com.phu.backend.exception.member;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class TrainerRoleException extends GlobalException {
    public static final String MESSAGE = "해당 기능은 트레이너만 가능합니다.";
    @Override
    public String getStatusCode() {
        return "M004";
    }
    public TrainerRoleException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
