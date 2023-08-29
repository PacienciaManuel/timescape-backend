package com.timescape.exception;

@SuppressWarnings("serial")
public class JWTServiceException extends RuntimeException {
	public JWTServiceException(String message) {
		super(message);
	}
	public JWTServiceException(Throwable throwable) {
		super(throwable);
	}
}
