package de.doridian.steammobile.auth.base;

import de.doridian.steammobile.auth.Credentials;

public interface AuthStorage {
	public void setCredentials(Credentials credentials);
	public Credentials getCredentials();
}
