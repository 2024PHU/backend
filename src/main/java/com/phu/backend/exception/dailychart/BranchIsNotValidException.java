package com.phu.backend.exception.dailychart;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class BranchIsNotValidException extends GlobalException {
    public static final String MESSAGE = "데일리 차트의 분기가 잘못됐습니다.";
    @Override
    public String getStatusCode() {
        return "D001";
    }
    public BranchIsNotValidException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
