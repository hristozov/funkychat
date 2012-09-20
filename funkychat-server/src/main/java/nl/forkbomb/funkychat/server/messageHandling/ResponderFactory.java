package nl.forkbomb.funkychat.server.messageHandling;

import com.google.inject.Inject;
import com.google.inject.Injector;

import nl.forkbomb.funkychat.server.messageHandling.responders.Responder;

public class ResponderFactory {
	//XXX: A VERY BAD IDEA. Try to use provider with assisted inject instead.
	@Inject private Injector injector;
	
	public Responder getResponder(Class<? extends Responder> clazz) {
		return injector.getInstance(clazz);
	}
}
