package de.doridian.steammobile.methods;

public class RequestException extends Exception {
	public RequestException(String error, String errorCode, String message) {
		super(message);
		this.error = error;
		this.errorCode = errorCode;
	}
	
	public final String error;
	public final String errorCode;
}
