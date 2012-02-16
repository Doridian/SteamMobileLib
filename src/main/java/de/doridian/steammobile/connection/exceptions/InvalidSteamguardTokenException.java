package de.doridian.steammobile.connection.exceptions;

public class InvalidSteamguardTokenException extends LoginException {
	public InvalidSteamguardTokenException() {
		super("Invalid steam guard token");
	}
}
