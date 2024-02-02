package com.example.aspira.error;

import lombok.Getter;

@Getter
public class AsyncParseException extends RuntimeException {

    public AsyncParseException(String message) {
        super(message);
    }

}