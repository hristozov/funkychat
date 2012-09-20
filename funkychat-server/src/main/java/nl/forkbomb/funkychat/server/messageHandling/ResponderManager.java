package nl.forkbomb.funkychat.server.messageHandling;

import nl.forkbomb.funkychat.server.messageHandling.responders.Responder;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserSession;

import com.google.inject.ImplementedBy;

/**
 * Registers a responder for specific message. Uses the responder later for handling messages of
 * the specified type.
 * @author gh
 */
@ImplementedBy(ResponderManagerImpl.class)
public interface ResponderManager {
	String[] handleMessage(Message message, UserSession context) throws ResponderManagerException;
	
	void registerResponder(String message, Class<? extends Responder> clazz);
	
	void unRegisterResponder(String message);
}
