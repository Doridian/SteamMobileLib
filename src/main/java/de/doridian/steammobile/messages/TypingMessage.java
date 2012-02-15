package de.doridian.steammobile.messages;

public class TypingMessage extends Message {
	public TypingMessage(long timestamp, String steamid_other) {
		super(timestamp, steamid_other);
	}

	@Override
	public String getType() {
		return "typing";
	}
}
