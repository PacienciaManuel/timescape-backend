package com.timescape.storage;

@SuppressWarnings("serial")
public class StorageDeleteException extends StorageException {

	public StorageDeleteException(String message) {
		super(message);
	}

	public StorageDeleteException(String message, Throwable cause) {
		super(message, cause);
	}
}
