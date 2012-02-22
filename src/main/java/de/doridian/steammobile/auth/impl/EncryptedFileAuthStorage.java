package de.doridian.steammobile.auth.impl;

import de.doridian.steammobile.auth.Credentials;
import de.doridian.steammobile.auth.base.StreamAuthStorage;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Key;

public class EncryptedFileAuthStorage extends StreamAuthStorage {
	private File file;
	private Cipher cipher;
	private Key key;
	
	public EncryptedFileAuthStorage(File file, Key key) {
		this(file, key, null);
		try {
			this.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		} catch(Exception e) { }
	}
	
	public EncryptedFileAuthStorage(File file, Key key, Cipher cipher) {
		this.file = file;
		this.cipher = cipher;
		this.key = key;
	}

	@Override
	public void setCredentials(Credentials credentials) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			this.writeCredentialsTo(credentials, new CipherOutputStream(new FileOutputStream(file), cipher));
		} catch(Exception e) {
			System.out.println("Writing authentication file failed because:");
			e.printStackTrace();
		}
	}

	@Override
	public Credentials getCredentials() {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return this.readCredentialsFrom(new CipherInputStream(new FileInputStream(file), cipher));
		} catch(FileNotFoundException e) {
			//If we don't find the file, just fall through and return null
		} catch(Exception e) {
			System.out.println("Reading authentication file failed because:");
			e.printStackTrace();
		}
		return null;
	}
}
