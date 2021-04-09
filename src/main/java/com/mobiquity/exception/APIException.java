package com.mobiquity.exception;

/**
 * The Class APIException.
 */
public class APIException extends Exception {
	private static final long serialVersionUID = -3141434677395845102L;

	/**
	 * Instantiates a new API exception.
	 *
	 * @param message the message
	 * @param e the exception
	 */
	public APIException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * Instantiates a new API exception.
	 *
	 * @param message exception message
	 */
	public APIException(String message) {
		super(message);
	}
}
