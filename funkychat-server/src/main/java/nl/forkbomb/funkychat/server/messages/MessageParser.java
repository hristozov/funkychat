package nl.forkbomb.funkychat.server.messages;

import org.apache.commons.lang3.ArrayUtils;

/**
 * A (stateful) request parser. We'll create a new instance for every request.
 * @author gh
 */
public class MessageParser {
	private boolean isInFileTransfer;
	
	/**
	 * Factory method for creating {@link MessageParser} instances.
	 */
	public static MessageParser getParser() {
		return new MessageParser();
	}
	
	private MessageParser() {
		isInFileTransfer = false;
	}
	
	/**
	 * Parses one line of input.
	 * @param line The input string.
	 * @return The resulting message.
	 * @throws MessageParserException If the message is malformed.
	 */
	public Message parseLine(String line) throws MessageParserException {
		String[] lineComponents = line.split("\\s+");
		if (lineComponents.length < 1) {
			throw new MessageParserException("Invalid message: " + line);
		}
		String[] args = ArrayUtils.remove(lineComponents, 0);
		String message = lineComponents[0];
				
		MessageConsts[] messages = MessageConsts.values();
		
		boolean messageValid = false;
		for (MessageConsts messageConsts : messages) {
			if (messageConsts.getMessage().equals(message))
			{
				if (MessageConsts.SEND_FILE_TO.getMessage().equals(message)) {
					isInFileTransfer = true;
				} else {
					isInFileTransfer = false;
				}
				messageValid = true;
				break;
			}
		}
		
		if (!messageValid && !isInFileTransfer) {
			throw new MessageParserException("Invalid message: " + line);
		}
		
		return new Message(lineComponents[0], args);
	}
}
