package de.doridian.steammobile.auth.base;

import de.doridian.steammobile.auth.Credentials;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class StreamAuthStorage extends ReaderWriterAuthStorage {
	protected void writeCredentialsTo(Credentials credentials, OutputStream stream) {
		writeCredentialsTo(credentials, new OutputStreamWriter(stream));
	}

	protected Credentials readCredentialsFrom(InputStream stream) {
		return readCredentialsFrom(new InputStreamReader(stream));
	}
}
