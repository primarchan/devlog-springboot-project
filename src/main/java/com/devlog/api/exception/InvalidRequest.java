package com.devlog.api.exception;

import lombok.Getter;

/**
 * status -> 400
 */
@Getter
public class InvalidRequest extends DevlogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String messgae) {
        super(MESSAGE);
        addValidation(fieldName, messgae);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
