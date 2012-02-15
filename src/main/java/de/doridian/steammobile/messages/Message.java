package de.doridian.steammobile.messages;

public class Message {
	public final long timestamp;
	public final String steamid_from;
	public Message(long timestamp, String steamid_from) {
		this.timestamp = timestamp;
		this.steamid_from = steamid_from;
	}
}
