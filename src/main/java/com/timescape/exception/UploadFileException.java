package com.timescape.exception;

@SuppressWarnings("serial")
public class UploadFileException extends RuntimeException {
	
	public UploadFileException(String message) {
		super(message);
	}
	
	public UploadFileException(Throwable throwable) {
		super(throwable);
	}
}
