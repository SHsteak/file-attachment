package com.example.fileAttachment.mongo;

public class MongoFileException extends RuntimeException {

	public MongoFileException(String message) {
		super(message);
	}

	public MongoFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
