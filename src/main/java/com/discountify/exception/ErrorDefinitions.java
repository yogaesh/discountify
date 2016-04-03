package com.discountify.exception;

public enum ErrorDefinitions {
	FILE(0, "Error in reading file.");

	private ErrorDefinitions(int code, String message) {
		this.code = code;
		this.description = message;
	}

	private final int code;
	private final String description;

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}