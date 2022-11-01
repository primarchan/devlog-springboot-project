package com.devlog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class DevlogException  extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public DevlogException(String message) {
        super(message);
    }

    public DevlogException(String message, Throwable cause) {
        super(message, cause);
    }
    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

}
