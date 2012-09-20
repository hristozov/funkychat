package nl.forkbomb.funkychat.server.messageHandling.responders;


import java.util.Collection;

import nl.forkbomb.funkychat.server.messageHandling.responses.ErrorResponse;
import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserSession;
import nl.forkbomb.funkychat.server.userMessages.UserMessage;
import nl.forkbomb.funkychat.server.userMessages.UserMessageQueue;
import nl.forkbomb.funkychat.server.userMessages.UserMessageQueueException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

public class SendToResponder implements Responder {
	@Inject private UserMessageQueue messageQueue;
	@Inject private UserRegistry userRegistry;
	
	@Override
	public Response handleAction(Message message, UserSession context) {
		if (context.username == null || context.username.length() == 0) {
			return new ErrorResponse("register first!");
		}
		String target = message.arguments[0];
		String messageText = StringUtils.join(ArrayUtils.remove(message.arguments, 0), ' ');
		
		Collection<UserSession> targetSession = userRegistry.getUser(target);
		if (targetSession.size() == 0) {
			return new ErrorResponse("no such user");
		}
		
		long messageId = -1;
		for (UserSession session : targetSession) {
			UserMessage userMessage = UserMessage.getMessage(context, session, messageText);
			try {
				messageId = messageQueue.submit(userMessage);
			} catch (UserMessageQueueException e) {
				return new ErrorResponse(e.getMessage());
			}
		}
		
		return new ResultResponse("message sent with id " + messageId);
	}

}
