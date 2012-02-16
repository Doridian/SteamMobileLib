package de.doridian.steammobile.connection.exceptions;

public class RequireSteamguardTokenException extends LoginException {
	public RequireSteamguardTokenException() {
		super("Invalid steam guard token");
	}
}
