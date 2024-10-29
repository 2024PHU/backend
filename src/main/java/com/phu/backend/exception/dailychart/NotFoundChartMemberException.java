package com.phu.backend.exception.dailychart;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundChartMemberException extends GlobalException {
    public static final String MESSAGE = "트레이너의 회원목록에 해당 회원이 존재하지 않습니다.";
    @Override
    public String getStatusCode() {
        return "D003";
    }
    public NotFoundChartMemberException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
