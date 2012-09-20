package nl.forkbomb.funkychat.server.messages;

import nl.forkbomb.funkychat.server.messageHandling.responders.ByeResponder;
import nl.forkbomb.funkychat.server.messageHandling.responders.ListResponder;
import nl.forkbomb.funkychat.server.messageHandling.responders.LoginResponder;
import nl.forkbomb.funkychat.server.messageHandling.responders.PingResponder;
import nl.forkbomb.funkychat.server.messageHandling.responders.Responder;
import nl.forkbomb.funkychat.server.messageHandling.responders.SendFileToResponder;
import nl.forkbomb.funkychat.server.messageHandling.responders.SendToResponder;

/**
 * Holds the message names and their corresponding handler classes.
 * @author gh
 */
public enum MessageConsts {
	USER("user", LoginResponder.class),
	LIST("list", ListResponder.class),
	BYE("bye", ByeResponder.class),
	SEND_TO("send_to", SendToResponder.class),
	SEND_FILE_TO("send_file_to", SendFileToResponder.class),
	PING("ping", PingResponder.class);
	
	private String message;
	private Class<? extends Responder> responder;
	
	public String getMessage() {
		return message;
	}
	
	public Class<? extends Responder> getResponder() {
		return responder;
	}
	
	MessageConsts(String message, Class<? extends Responder> responder) {
		this.message = message;
		this.responder = responder;
	}
}
