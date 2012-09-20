package nl.forkbomb.funkychat.server.messageHandling.responders;

import nl.forkbomb.funkychat.server.messageHandling.responses.ErrorResponse;
import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserRegistryException;
import nl.forkbomb.funkychat.server.user.UserSession;

import com.google.inject.Inject;

public class LoginResponder implements Responder {
	private UserRegistry userRegistry;

	@Inject
	public LoginResponder(UserRegistry userRegistry) {
		this.userRegistry = userRegistry;
	}

	public Response handleAction(Message message, UserSession context) {
		String username = message.arguments[0];
		if (userRegistry.isUserRegistered(username)) {
			return new ErrorResponse("err " + username + " already taken!");
		}

		//TODO: check if the context is already logged in
		try {
			userRegistry.registerUser(username, context);
			context.username = username;
		} catch (UserRegistryException e) {
			e.printStackTrace();
		}
		return new ResultResponse("ok " + username + " successfully registered!");
	}
}
