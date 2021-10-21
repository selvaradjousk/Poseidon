package com.nnk.springboot.exception;

/**
 * The Class DataNotFoundException.
 */
public class DataNotFoundException extends RuntimeException {

    /**
     * Instantiates a new data not found exception.
     *
     * @param message the message
     */
    public DataNotFoundException(final String message) {
        super(message);
    }
}
