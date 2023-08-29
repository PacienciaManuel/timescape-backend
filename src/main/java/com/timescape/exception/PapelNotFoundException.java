package com.timescape.exception;

@SuppressWarnings("serial")
public class PapelNotFoundException extends RuntimeException {
	public PapelNotFoundException(String message) {
		super(message);
	}
}
