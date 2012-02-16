package de.doridian.steammobile.messages;

@Message.Type("leftconversation")
public class LeftConversationMessage extends Message {
	public LeftConversationMessage(long timestamp, String steamid_other) {
		super(timestamp, steamid_other);
	}
}
