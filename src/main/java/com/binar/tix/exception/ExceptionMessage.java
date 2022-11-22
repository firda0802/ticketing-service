package com.binar.tix.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionMessage {
    private String errorMessage;
    private String description;

    public ExceptionMessage(String errorMessage, String description) {
        this.errorMessage = errorMessage;
        this.description = description;
    }
}
