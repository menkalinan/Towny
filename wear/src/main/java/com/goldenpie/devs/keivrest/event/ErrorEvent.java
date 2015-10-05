package com.goldenpie.devs.keivrest.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(suppressConstructorProperties = true)
@Data
public class ErrorEvent {
    private String error;
}
