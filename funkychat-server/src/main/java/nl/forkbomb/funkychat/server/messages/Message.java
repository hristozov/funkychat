package nl.forkbomb.funkychat.server.messages;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * A value object that holds the information about a message.
 * @author gh
 */
public class Message {
	/**
	 * The type of message received.
	 */
	public String type;
	/**
	 * The additional arguments for the message.
	 */
	public String[] arguments;
	
	/**
	 * Creates a message.
	 */
	public Message(String type, String[] arguments) {
		this.type = type;
		this.arguments = arguments;
		if (arguments == null) {
			arguments = new String[0];
		}
	}
	
	/**
	 * Creates a message.
	 */
	public Message() {
		
	}
	
	@Override
	public boolean equals(Object otherMessage) {
		if (otherMessage == null || !(otherMessage instanceof Message)) {
			return false;
		}
		
		if (otherMessage == this) {
			return true;
		}
		
		Message other = (Message)otherMessage;
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
	@Override
	public String toString() {
		return type + " " + StringUtils.join(arguments, " ");
	}
	
	public Message(String type) {
		this.type = type;
	}
}
