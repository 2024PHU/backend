package com.phu.backend.exception.summarization;

import com.phu.backend.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundSummarizationException extends GlobalException {
    public static final String MESSAGE = "해당 회원에 대한 요약본을 찾을 수 없습니다.";
    @Override
    public String getStatusCode() {
        return "SU001";
    }
    public NotFoundSummarizationException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
