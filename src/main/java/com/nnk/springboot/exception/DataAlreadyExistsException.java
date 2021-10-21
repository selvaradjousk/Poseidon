package com.nnk.springboot.exception;

/**
 * The Class DataAlreadyExistsException.
 */
public class DataAlreadyExistsException extends RuntimeException {

	/**
	 * Instantiates a new data already exists exception.
	 *
	 * @param message the message
	 */
	public DataAlreadyExistsException(final String message) {
	    super(message);
	}
}
