package de.doridian.steammobile.messages;

public class TextMessage extends Message {
	public final String text;
	public TextMessage(long timestamp, String steamid_from, String text) {
		super(timestamp, steamid_from);
		this.text = text;
	}
}
