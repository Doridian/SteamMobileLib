package de.doridian.steammobile.auth;

public class Credentials {
	public String username;
	public String password;
	public String steamguard_token;

	public Credentials(String username, String password, String steamguard_token) {
		this.username = username;
		this.password = password;
		this.steamguard_token = steamguard_token;
	}

	public Credentials() {
		this(null, null, null);
	}
}
