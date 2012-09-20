package nl.forkbomb.funkychat.server.dependencyInjection;

import nl.forkbomb.funkychat.server.messageHandling.ResponderManager;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.userMessages.UserMessageQueue;
import nl.forkbomb.funkychat.server.worker.Worker;
import nl.forkbomb.funkychat.server.worker.WorkerFactory;
import nl.forkbomb.funkychat.server.worker.WorkerFactory.RealWorker;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * A {@link Module} used for configuring aspects of dependency injection.
 * @author gh
 */
public class ServerModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder()
	     .implement(Worker.class, RealWorker.class)
	     .build(WorkerFactory.class));
		
		/* XXX: An ugly way to avoid duplicating it with getInstance() */
		bind(ResponderManager.class).in(Scopes.SINGLETON);
		bind(UserRegistry.class).in(Scopes.SINGLETON);
		bind(UserMessageQueue.class).in(Scopes.SINGLETON);
	}

}
