package nl.forkbomb.funkychat.server.messages;

/**
 * Used when a malformed input is passed to the parser.
 * @author gh
 */
public class MessageParserException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public MessageParserException(String message) {
		super(message);
	}
}