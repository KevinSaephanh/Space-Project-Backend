package com.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -5872168290405061625L;

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}
