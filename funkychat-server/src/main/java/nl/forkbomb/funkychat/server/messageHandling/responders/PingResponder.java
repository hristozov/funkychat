package nl.forkbomb.funkychat.server.messageHandling.responders;

import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserSession;

public class PingResponder implements Responder {

	@Override
	public Response handleAction(Message message, UserSession context) {
		return new ResultResponse("pong");
	}

}
