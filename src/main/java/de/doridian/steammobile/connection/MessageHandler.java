package de.doridian.steammobile.connection;

import de.doridian.steammobile.friend.Friend;
import de.doridian.steammobile.messages.Message;
import de.doridian.steammobile.messages.PersonaRelationshipMessage;
import de.doridian.steammobile.messages.PersonaStateMessage;
import de.doridian.steammobile.methods.RequestException;
import de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth.Logoff;
import de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth.Logon;
import de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth.Poll;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MessageHandler {
	private final SteamConnection connection;
	private final DefaultMessageListener defaultMessageListener;

	private String lastMessageID;

	private boolean isLoggedOn = false;

	public MessageHandler(SteamConnection connection) {
		this.connection = connection;
		this.defaultMessageListener = new DefaultMessageListener();
		registerListener(this.defaultMessageListener);
	}

	public void logon() throws RequestException {
		if(isLoggedOn) return;
		Logon msg = new Logon(connection);
		lastMessageID = msg.send().get("message").toString();
		isLoggedOn = true;
	}
	
	public void logoff() throws RequestException {
		if(!isLoggedOn) return;
		Logoff msg = new Logoff(connection);
		msg.send();
		isLoggedOn = false;
	}
	
	public void sendMessage(Message message) throws RequestException {
		if(!isLoggedOn) return;
		de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth.Message msg = new de.doridian.steammobile.methods.api.ISteamWebUserPresenceOAuth.Message(connection);
		msg.setMessage(message);
		msg.send();
	}

	class DefaultMessageListener implements MessageListener {
		@MessageListener.Handler
		public void onPersonaStateChanged(PersonaStateMessage msg) {
			Friend friend;
			if(!connection.friends.containsKey(msg.steamid_other)) {
				String relationship = "3";
				if(msg instanceof PersonaRelationshipMessage) {
					relationship = ""+msg.status_flags;
				}
				friend = new Friend(connection, msg.steamid_other, relationship, System.currentTimeMillis() / 1000);
				connection.friends.put(msg.steamid_other, friend);
			} else {
				friend = connection.friends.get(msg.steamid_other);
			}
			friend.personaname = msg.persona_name;
			friend.personastate = msg.persona_state;
		}

		@MessageListener.Handler
		public void onPersonaRelationshipChanged(PersonaRelationshipMessage msg) {
			onPersonaStateChanged(msg);
		}
	}

	private int mstimeout = 5000;
	private boolean isThreaded = false;
	private boolean isThreadPollable = false;
	private List<Message> messageQueue = new ArrayList<Message>();
	private final Object threadLock = new Object();

	private static class MessageListenerMethod {
		private Method method;
		private MessageListener listener;
		private MessageListenerMethod(MessageListener listener, Method method) {
			this.method = method;
			this.listener = listener;
		}
	}
	private final HashSet<MessageListenerMethod> globalListeners = new HashSet<MessageListenerMethod>();
	private final HashMap<Class<? extends Message>, HashSet<MessageListenerMethod>> messageListeners = new HashMap<Class<? extends Message>, HashSet<MessageListenerMethod>>();

	public void registerListener(MessageListener listener) {
		Method[] methods = listener.getClass().getMethods();
		for(Method method : methods) {
			if(!method.isAnnotationPresent(MessageListener.Handler.class)) continue;
			Class[] params = method.getParameterTypes();
			if(params.length != 1) continue;
			Class paramOne = params[0];
			if(!Message.class.isAssignableFrom(paramOne)) continue;
			Class<? extends Message> forMessage = (Class<? extends Message>)paramOne;

			try {
				method.setAccessible(true); //So we can use internal classes, thanks reflection!
			} catch(Exception e) { }
			if(!method.isAccessible()) continue; //We failed to make it accessible, so ditch it!

			HashSet<MessageListenerMethod> list;
			if(forMessage.equals(Message.class)) {
				list = globalListeners;
			} else if(messageListeners.containsKey(forMessage)) {
				list = messageListeners.get(forMessage);
			} else {
				list = new HashSet<MessageListenerMethod>();
				messageListeners.put(forMessage, list);
			}
			list.add(new MessageListenerMethod(listener, method));
		}
	}

	public List<Message> getMessages() throws RequestException {
		if(!isLoggedOn) return null;
		if(isThreaded) {
			if(isThreadPollable) {
				List<Message> queue;
				synchronized(threadLock) {
					queue = messageQueue;
					messageQueue = new ArrayList<Message>();
				}
				return queue;
			} else {
				return null;
			}
		} else {
			return __getMessages();
		}
	}

	public void stopGetMessagesLoop() {
		isThreaded = false;
	}

	public void startGetMessagesLoop() {
		startGetMessagesLoop(false);
	}

	//usePolling == true means you still want to use getMessages just without it being blocking
	//usePolling == false means you want to use the event based system (causing getMessages() to ALWAYS return null!)
	public void startGetMessagesLoop(final boolean usePolling) {
		isThreaded = true;
		isThreadPollable = usePolling;
		new Thread() {
			@Override
			public void run() {
				while(isThreaded) {
					try {
						if(isLoggedOn) {
							List<Message> msgs = __getMessages();
							if(usePolling) {
								synchronized(threadLock) {
									messageQueue.addAll(msgs);
								}
							} else {
								for(Message msg : msgs) {
									//Global listeners
									for(MessageListenerMethod item : globalListeners) {
										item.method.invoke(item.listener, msg);
									}

									//Specific listeners
									Class<? extends Message> clazz = msg.getClass();
									if(!messageListeners.containsKey(clazz)) continue;
									HashSet<MessageListenerMethod> methods = messageListeners.get(clazz);
									for(MessageListenerMethod item : methods) {
										item.method.invoke(item.listener, msg);
									}
								}
							}
						}
						Thread.sleep(mstimeout);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				synchronized (threadLock) {
					messageQueue.clear();
				}
			}
		}.start();
	}

	private List<Message> __getMessages() throws RequestException {
		Poll msg = new Poll(connection);
		msg.setLastMessageID(lastMessageID);
		List<Message> ret = new ArrayList<Message>();
		try {
			JSONObject obj = msg.send();

			if(obj.containsKey("messagelast")) {
				lastMessageID = obj.get("messagelast").toString();
			}

			if(!obj.containsKey("messages")) return ret;

			JSONArray arr = (JSONArray)obj.get("messages");
			for(Object ent : arr) {
				JSONObject entj = (JSONObject)ent;
				Message recvd = Message.craftMessageFromJSON(entj);
				if(recvd != null) {
					ret.add(recvd);
				} else {
					System.out.println("Unknown message: " + entj.toJSONString());
				}
			}

			if(obj.containsKey("sectimeout")) {
				mstimeout = Integer.valueOf(obj.get("sectimeout").toString()) * 1000;
				if(mstimeout > 5000) mstimeout = 5000;
			}

			return ret;
		} catch(RequestException e) {
			if(e.error.equalsIgnoreCase("Timeout")) {
				return ret;
			}
			throw e;
		}
	}
}