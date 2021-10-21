package com.nnk.springboot.exception;

/**
 * The Class JwtTokenMissingException.
 */
public class JwtTokenMissingException extends RuntimeException {

    /**
     * Instantiates a new jwt token missing exception.
     *
     * @param message the message
     */
    public JwtTokenMissingException(final String message) {
        super(message);
    }
}
