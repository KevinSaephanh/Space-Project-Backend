package com.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 949302012582863103L;

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}
}
