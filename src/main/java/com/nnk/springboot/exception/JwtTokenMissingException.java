package com.nnk.springboot.exception;

public class JwtTokenMissingException extends RuntimeException {

    public JwtTokenMissingException(final String message) {
        super(message);
    }
}
