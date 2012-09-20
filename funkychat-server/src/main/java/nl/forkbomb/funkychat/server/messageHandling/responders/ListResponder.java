package nl.forkbomb.funkychat.server.messageHandling.responders;

import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserSession;

import com.google.inject.Inject;

public class ListResponder implements Responder {
	private UserRegistry userRegistry;

	@Inject
	public ListResponder(UserRegistry userRegistry) {
		this.userRegistry = userRegistry;
	}

	@Override
	public Response handleAction(Message message, UserSession context) {
		return new ResultResponse(userRegistry.getNamesOfRegisteredUsers());
	}
}
