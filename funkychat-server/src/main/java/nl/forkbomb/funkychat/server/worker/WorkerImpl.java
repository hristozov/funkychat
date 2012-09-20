package nl.forkbomb.funkychat.server.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import nl.forkbomb.funkychat.server.messageHandling.ResponderManager;
import nl.forkbomb.funkychat.server.messageHandling.ResponderManagerException;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.messages.MessageParser;
import nl.forkbomb.funkychat.server.messages.MessageParserException;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserSession;

import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * A worker that persists the user session and handles input and output.
 * @author gh
 */
public class WorkerImpl implements Runnable, Worker {
	private final Socket server;
	private final MessageParser messageParser;
	private final UserSession session;
	private final UserRegistry registry;
	private ResponderManager responderManager;
	private BufferedReader in;	
	private PrintWriter out;
	
	//TODO: Real start/stop logic instead of this mess.
	private boolean shouldMainLoopRun = true;

	/**
	 * A boring constructor.
	 */
	@Inject
	public WorkerImpl(ResponderManager responderManager, UserRegistry userRegistry, @Assisted Socket server) {
		this.server = server;
		this.responderManager = responderManager;
		this.messageParser = MessageParser.getParser();
		this.registry = userRegistry;
		this.session = UserSession.getUserSession(this);
		registry.registerUser(session);
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			out = new PrintWriter(server.getOutputStream(), true);
			session.lastContact = System.currentTimeMillis();
			LoggerFactory.getLogger(getClass()).debug("Connection from "  + server.getInetAddress());
			mainLoop();
			server.close(); //XXX: finally?
		} catch (IOException e) {
			System.out.println("Worker failed.");
			e.printStackTrace();
		}
	}
	
	public void stop() {
		shouldMainLoopRun = false;
		try {
			in.close();
		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).error("Could not close input stream in stop()", e);
		}
	}
	
	public void sendToClient(String line) {
		LoggerFactory.getLogger(getClass()).debug("Writing to client: " + line);
		out.println(line);
	}
	
	public void sendToClient(String[] lines) {
		for (String line : lines) {
			sendToClient(line);
		}
	}
	
	private void mainLoop() {
		while (shouldMainLoopRun) {
			try {
				String inputLine = in.readLine();
				LoggerFactory.getLogger(getClass()).debug("Read from client: " + inputLine);
				session.lastContact = System.currentTimeMillis();
				Message message = this.messageParser.parseLine(inputLine);
				String[] response = responderManager.handleMessage(message, session);
				for (String line : response) {
					out.println(line);
				}
			} catch (MessageParserException|ResponderManagerException e) {
				out.println("WTF?");
			} catch (IOException e) {
				out.println("Invalid input");
				return;
			}
		}
	}
}
