package com.goldenpie.devs.kievrest.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ErrorEvent<T> {
    private final String error;

    public String getError() {
        return error == null ? "" : error;
    }
}
