package nl.forkbomb.funkychat.server.messageHandling;

import java.util.HashMap;
import java.util.Map;

import nl.forkbomb.funkychat.server.messageHandling.responders.Responder;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserSession;

import com.google.inject.Inject;


/**
 * The implementation for {@link ResponderManager}.
 * @author gh
 */
public class ResponderManagerImpl implements ResponderManager {
	@Inject private ResponderFactory responderFactory;
	
	private Map<String, Responder> responderRegistry = new HashMap<String, Responder>();

	@Override
	public final String[] handleMessage(final Message message, final UserSession context)
			throws ResponderManagerException {
		Responder handler = responderRegistry.get(message.type);
		if (handler == null) {
			throw new ResponderManagerException("Invalid message!");
		}
		return handler.handleAction(message, context).toListOfLines();
	}
	
	@Override
	public void registerResponder(String message,
			Class<? extends Responder> clazz) {
		Responder responder = responderFactory.getResponder(clazz);
		responderRegistry.put(message, responder);
	}

	@Override
	public final void unRegisterResponder(final String message) {
		responderRegistry.remove(message);		
	}
}
