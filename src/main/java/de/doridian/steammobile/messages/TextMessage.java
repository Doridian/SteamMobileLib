package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

import java.util.Map;

@Message.Type("saytext")
public class TextMessage extends Message {
	public final String text;

	public TextMessage(JSONObject json) {
		super(json);
		this.text = json.get("text").toString();
	}

	public TextMessage(String steamid_other, String text) {
		super(steamid_other);
		this.text = text;
	}

	@Override
	public void addParameters(Map<String, String> params) {
		params.put("text", text);
	}
}
