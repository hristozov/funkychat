package nl.forkbomb.funkychat.server.worker;

import java.net.Socket;

import nl.forkbomb.funkychat.server.messageHandling.ResponderManager;
import nl.forkbomb.funkychat.server.user.UserRegistry;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Factory for {@link Worker} instances. Use it from Guice injectors.
 * @author gh
 */
public interface WorkerFactory {
	/**
	 * Creates a new {@link Worker}, associated with the given {@link Socket}.
	 * @param server The connection {@link Socket}.
	 * @return The resulting {@link Worker}
	 */
	WorkerImpl create(Socket server);
	
	/**
	 * This class will be used later by Guice. Do not use it directly.
	 * @author gh
	 */
	public abstract class RealWorker implements Worker {
		/**
		 * A boring constructor.
		 */
		@AssistedInject
		public RealWorker(ResponderManager responderManager, UserRegistry registry, @Assisted Socket server) {
			
		}
	}
}
