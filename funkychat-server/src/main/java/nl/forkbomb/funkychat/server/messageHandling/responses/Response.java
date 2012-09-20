package nl.forkbomb.funkychat.server.messageHandling.responses;

/**
 * An object that holds the result for a specific message.
 * @author gh
 *
 */
public interface Response {
	/**
	 * Return a list of lines which should be returned to the client.
	 */
	String[] toListOfLines();
}
