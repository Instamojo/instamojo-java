package com.instamojo.wrapper.exception;

/**
 * The Class InvalidRefundException.
 */
public class InvalidRefundException extends InstamojoBaseException {

	/**
	 * Instantiates a new invalid refund exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public InvalidRefundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new invalid refund exception.
	 */
	public InvalidRefundException() {
	}

	/**
	 * Instantiates a new invalid refund exception.
	 *
	 * @param message the message
	 */
	public InvalidRefundException(String message) {
		super(message);
	}
}
