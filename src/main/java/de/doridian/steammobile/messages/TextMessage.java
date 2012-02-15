package de.doridian.steammobile.messages;

import java.util.Map;

public class TextMessage extends Message {
	public final String text;
	public TextMessage(long timestamp, String steamid_other, String text) {
		super(timestamp, steamid_other);
		this.text = text;
	}

	@Override
	public String getType() {
		return "saytext";
	}

	@Override
	public void addParameters(Map<String, String> params) {
		params.put("text", text);
	}
}
