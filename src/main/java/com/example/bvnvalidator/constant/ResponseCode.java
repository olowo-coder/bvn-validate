package com.example.bvnvalidator.constant;

public enum ResponseCode {
    SUCCESS("00", "Success"),
    EMPTY_BVN("400", "One or more of your request parameters failed validation. please retry"),
    BVN_NOT_EXIST("01", "The searched BVN does not exist"),
    BVN_LESS_THAN("02", "The searched BVN is invalid"),
    NON_DIGIT_BVN("400", "The searched BVN is invalid");

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
