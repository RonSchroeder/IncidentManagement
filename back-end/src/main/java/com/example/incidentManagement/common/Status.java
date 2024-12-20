package com.example.incidentManagement.common;

public enum Status {
    OPEN(1),
    IN_PROGRESS(2),
    CLOSED(0);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isValid(String value) {
        for (Status status : Status.values()) {
            if (status.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
