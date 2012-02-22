package de.doridian.steammobile.auth.base;

import de.doridian.steammobile.auth.Credentials;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public abstract class ReaderWriterAuthStorage implements AuthStorage {
	protected void writeCredentialsTo(Credentials credentials, Writer writer) {
		try {
			PrintWriter pwriter = new PrintWriter(writer);
			pwriter.println(credentials.username);
			pwriter.println(credentials.password);
			pwriter.println(credentials.steamguard_token);
			pwriter.close();
		} catch(Exception e) {
			System.out.println("Writing authentication file failed because:");
			e.printStackTrace();
		}
	}

	protected Credentials readCredentialsFrom(Reader reader) {
		try {
			BufferedReader breader = new BufferedReader(reader);
			String user = breader.readLine();
			String password = breader.readLine();
			String token = breader.readLine();
			breader.close();
			return new Credentials(user, password, token);
		} catch(FileNotFoundException e) {
			//We don't care!
		} catch(Exception e) {
			System.out.println("Reading authentication file failed because:");
			e.printStackTrace();
		}
		return null;
	}
}
