package com.example.aspira.error;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RestParseException extends RuntimeException {
    private final HttpStatusCode statusCode;

    public RestParseException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}

