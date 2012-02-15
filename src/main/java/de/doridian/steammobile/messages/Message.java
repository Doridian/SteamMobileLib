package de.doridian.steammobile.messages;

import java.util.Map;

public abstract class Message {
	public final long timestamp;
	public final String steamid_other;
	public Message(long timestamp, String steamid_other) {
		this.timestamp = timestamp;
		this.steamid_other = steamid_other;
	}

	public abstract String getType();

	public void addParameters(Map<String, String> params) {

	}
}
