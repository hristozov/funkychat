package nl.forkbomb.funkychat.server.messageHandling.responders;

import com.google.inject.Inject;

import nl.forkbomb.funkychat.server.messageHandling.responses.ErrorResponse;
import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserRegistryException;
import nl.forkbomb.funkychat.server.user.UserSession;

public class ByeResponder implements Responder {
	@Inject private UserRegistry userRegistry;
	public Response handleAction(Message message, UserSession context) {
		if (context.username != null && context.username.length() > 0) {
			try {
				userRegistry.unRegisterUser(context.username);
			} catch (UserRegistryException e) {
				return new ErrorResponse("FAIL");
			}
		}
		context.worker.stop();
		return new ResultResponse("bye ;)");
	}

}
