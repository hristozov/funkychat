package nl.forkbomb.funkychat.server.userMessages;

import com.google.inject.ImplementedBy;

@ImplementedBy(UserMessageQueueImpl.class)
public interface UserMessageQueue extends Runnable {
	public long submit(UserMessage message) throws UserMessageQueueException;
}
