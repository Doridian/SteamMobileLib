package de.doridian.steammobile.connection.exceptions;

public class InvalidUsernameOrPasswordException extends LoginException {
	public InvalidUsernameOrPasswordException() {
		super("Invalid steam guard token");
	}
}
