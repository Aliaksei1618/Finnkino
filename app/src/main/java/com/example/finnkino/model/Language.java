package com.example.finnkino.model;

public enum Language {

    ENGLISH ("en"),
    FINNISH("fi");

    private String isoCode;

    Language(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }
}
