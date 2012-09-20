package nl.forkbomb.funkychat.server.messageHandling.responders;

import nl.forkbomb.funkychat.server.messageHandling.responses.ErrorResponse;
import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserSession;

public class SendFileToResponder implements Responder {

	@Override
	public Response handleAction(Message message, UserSession context) {
		if (message.arguments.length != 3) {
			return getFailResponse();
		}
		return new ResultResponse("lol");
	}
	
	private Response getFailResponse() {
		return new ErrorResponse("server transfer error");
	}

}
