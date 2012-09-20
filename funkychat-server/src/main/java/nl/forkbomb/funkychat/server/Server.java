package nl.forkbomb.funkychat.server;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

import nl.forkbomb.funkychat.server.dependencyInjection.ServerModule;
import nl.forkbomb.funkychat.server.messageHandling.ResponderManager;
import nl.forkbomb.funkychat.server.messages.MessageConsts;
import nl.forkbomb.funkychat.server.network.Acceptor;
import nl.forkbomb.funkychat.server.user.UserRegistryCleaner;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Our main class :)
 * @author gh
 */
public class Server {
	/**
	 * 
	 */
	public static void main(String[] args) throws IOException {
		final Injector injector = Guice.createInjector(new ServerModule());
		Acceptor acceptor = injector.getInstance(Acceptor.class);
			
		registerResponders(injector);
		addCleanerTask(injector);
		Runtime.getRuntime().addShutdownHook(new ShutdownHook(acceptor));
		
		acceptor.start();
	}
	
	private static void registerResponders(Injector injector) {
		ResponderManager responderManager = injector.getInstance(ResponderManager.class);
		
		EnumSet<MessageConsts> messagesSet = EnumSet.allOf(MessageConsts.class);
		for (MessageConsts messageConsts : messagesSet) {
			responderManager.registerResponder(messageConsts.getMessage(), messageConsts.getResponder());
		}
	}
	
	private static void addCleanerTask(Injector injector) {
		TimerTask task = injector.getInstance(UserRegistryCleaner.class);
		(new Timer()).scheduleAtFixedRate(task, 5 * 1000, 10 * 1000);
	}
}

final class ShutdownHook extends Thread {
	private Acceptor acceptor;
	
	public ShutdownHook(Acceptor acceptor) {
		this.acceptor = acceptor;
	}
	
	@Override
	public void run() {
		/* Graceful... */
		this.acceptor.shutdown();
	}
}
