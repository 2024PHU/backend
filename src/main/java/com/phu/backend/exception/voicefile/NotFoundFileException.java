package com.phu.backend.exception.voicefile;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundFileException extends GlobalException {
    public static final String MESSAGE = "음성파일을 찾을 수 없습니다.";
    @Override
    public String getStatusCode() {
        return "VF001";
    }
    public NotFoundFileException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
