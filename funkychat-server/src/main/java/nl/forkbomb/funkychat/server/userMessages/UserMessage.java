package nl.forkbomb.funkychat.server.userMessages;

import nl.forkbomb.funkychat.server.user.UserSession;


public class UserMessage {
	//XXX: getters
	public UserSession sender;
	public UserSession receiver;
	public String message;
	
	public static UserMessage getMessage(UserSession sender, UserSession receiver, String message) {
		return new UserMessage(sender, receiver, message);
	}
	
	private UserMessage(UserSession sender, UserSession receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
}
