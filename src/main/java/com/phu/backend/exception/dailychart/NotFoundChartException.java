package com.phu.backend.exception.dailychart;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundChartException extends GlobalException {
    public static final String MESSAGE = "데일리 차트를 찾지 못했습니다.";
    @Override
    public String getStatusCode() {
        return "D002";
    }
    public NotFoundChartException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
