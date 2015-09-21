package com.goldenpie.devs.kievrest.models;

import java.util.Map;

import lombok.Getter;

@Getter
public class ErrorBody {
    private Map<String, String> errors;
}
