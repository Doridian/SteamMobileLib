package de.doridian.steammobile.auth.impl;

import de.doridian.steammobile.auth.Credentials;
import de.doridian.steammobile.auth.base.ReaderWriterAuthStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class FlatFileAuthStorage extends ReaderWriterAuthStorage {
	private File file;
	public FlatFileAuthStorage(File file) {
		this.file = file;
	}

	@Override
	public void setCredentials(Credentials credentials) {
		try {
			this.writeCredentialsTo(credentials, new FileWriter(file));
		} catch(Exception e) {
			System.out.println("Writing authentication file failed because:");
			e.printStackTrace();
		}
	}

	@Override
	public Credentials getCredentials() {
		try {
			return this.readCredentialsFrom(new FileReader(file));
		} catch(FileNotFoundException e) {
			//If we don't find the file, just fall through and return null
		} catch(Exception e) {
			System.out.println("Reading authentication file failed because:");
			e.printStackTrace();
		}
		return null;
	}
}
