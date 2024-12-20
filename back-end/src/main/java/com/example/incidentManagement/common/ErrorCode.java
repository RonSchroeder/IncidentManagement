package com.example.incidentManagement.common;

public enum ErrorCode {
    SUCCESS(0),
    FAILURE(1);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
