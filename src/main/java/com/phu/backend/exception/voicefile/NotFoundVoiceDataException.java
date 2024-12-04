package com.phu.backend.exception.voicefile;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundVoiceDataException extends GlobalException {
    public static final String MESSAGE = "텍스트 변환 데이터를 찾을 수 없습니다.";
    @Override
    public String getStatusCode() {
        return "VF003";
    }
    public NotFoundVoiceDataException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
