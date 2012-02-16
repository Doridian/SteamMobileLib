package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("my_saytext")
public class MyTextMessage extends TextMessage {
	public MyTextMessage(JSONObject json) {
		super(json);
	}

	public MyTextMessage(String steamid_other, String text) {
		super(steamid_other, text);
	}
}
