package nl.forkbomb.funkychat.server.worker;

/**
 * A placeholder for marking workers, required for DI.
 * @see WorkerFactory
 * @author gh
 */
public interface Worker {
	void stop();
	
	void sendToClient(String line);
	
	void sendToClient(String[] lines);
}
