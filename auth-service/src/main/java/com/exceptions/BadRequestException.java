package com.exceptions;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -4683589242203615733L;

	public BadRequestException(String message) {
		super(message);
	}
}
