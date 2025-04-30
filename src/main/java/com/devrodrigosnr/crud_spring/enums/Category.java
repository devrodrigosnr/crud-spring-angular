package com.devrodrigosnr.crud_spring.enums;

public enum Category {
    BACKEND("Backend"), FRONTEND("Frontend");


    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
