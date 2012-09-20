package nl.forkbomb.funkychat.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nl.forkbomb.funkychat.server.userMessages.UserMessageQueue;
import nl.forkbomb.funkychat.server.worker.Worker;
import nl.forkbomb.funkychat.server.worker.WorkerFactory;

import com.google.inject.Inject;

/**
 * The acceptor, responsible for managing the connection thread pool and passing the incoming
 * messages to the appropriate {@link Worker} instances.
 * @author gh
 */
public class Acceptor {
	@Inject private WorkerFactory workerFactory;
	@Inject private UserMessageQueue messageQueue;
	
	private ExecutorService service;

	private int port;

	/**
	 * Creates an {@link Acceptor}.
	 * @param port The desired port to listen to.
	 */
	public Acceptor(int port) {
		this.port = port;
		this.service = Executors.newCachedThreadPool();
	}

	/**
	 * Creates an {@link Acceptor}.
	 */
	public Acceptor() {
		this(1337);
	}

	/**
	 * Starts the {@link Acceptor}. Our server will bind to the port and accept connections from
	 * now on.
	 */
	public void start() {
		try {
			service.submit(messageQueue);
			final ServerSocket listener = new ServerSocket(this.port);
			Socket server;
			while (true) {
				server = listener.accept();
				service.submit(workerFactory.create(server));
			}
		} catch (IOException e) {
			System.out.println("Acceptor failed.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempts a graceful shutdown, which will finish all work first.
	 */
	public void shutdown() {
		service.shutdown();
	}
	
	/**
	 * Shutdowns now, killing all {@link Worker}s and generally fucking up user connections.
	 */
	public void shutdownNow() {
		service.shutdownNow();
	}
}
