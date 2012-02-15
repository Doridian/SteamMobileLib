package de.doridian.steammobile;

import java.io.*;
import java.util.Scanner;

public class Main {
	public static final Scanner input = new Scanner(System.in);

	private static final String AUTHFILE = "auth.cfg";
	
	public static void main(String[] args) {
		File file = new File(AUTHFILE);
		
		String user; String password; String token;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			user = reader.readLine();
			password = reader.readLine();
			token = reader.readLine();
			reader.close();
		} catch(Exception e) {
			System.out.print("Username: ");
			user = input.nextLine();
			System.out.print("Password: ");
			password = input.nextLine();
			token = null;
			writeAuthFile(user, password, null);
		}

		new SteamConnection(user, password, token);
	}
	
	public static void writeAuthFile(String username, String password, String token) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(AUTHFILE));
			writer.println(username);
			writer.println(password);
			writer.println(token);
			writer.close();
		} catch(Exception e) {
			
		}
	}
}
