package com.phu.backend.exception.voicefile;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotAnalyzeFileException extends GlobalException {
    public static final String MESSAGE = "음성 텍스트를 변환하는데 실패했습니다.";
    @Override
    public String getStatusCode() {
        return "VF002";
    }
    public NotAnalyzeFileException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
