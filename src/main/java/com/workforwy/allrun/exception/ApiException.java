package com.workforwy.allrun.exception;

public class ApiException extends RuntimeException {

    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ApiException unauthorized(String message) {
        return new ApiException(102, message);
    }

    public static ApiException badRequest(String message) {
        return new ApiException(1, message);
    }

    public static ApiException conflict(String message) {
        return new ApiException(201, message);
    }

    public static ApiException noFile(String message) {
        return new ApiException(401, message);
    }

    public static ApiException noUpdate(String message) {
        return new ApiException(301, message);
    }

    public static ApiException serverError(String message) {
        return new ApiException(101, message);
    }
}
