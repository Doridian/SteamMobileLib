package de.doridian.steammobile.messages;

import org.json.simple.JSONObject;

@Message.Type("leftconversation")
public class LeftConversationMessage extends Message {
	public LeftConversationMessage(JSONObject json) {
		super(json);
	}

	public LeftConversationMessage(String steamid_other) {
		super(steamid_other);
	}
}
