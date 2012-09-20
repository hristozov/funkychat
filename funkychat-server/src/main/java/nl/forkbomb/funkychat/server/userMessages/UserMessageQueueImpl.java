package nl.forkbomb.funkychat.server.userMessages;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMessageQueueImpl implements UserMessageQueue {
	private Queue<UserMessage> messageQueue;
	private AtomicLong messageId;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ReadWriteLock monitor = new ReentrantReadWriteLock();

	private static final int PROCESS_INTERVAL = 5 * 1000;

	@Override
	public void run() {
		while(true) {
			processQueue();
			try {
				Thread.sleep(PROCESS_INTERVAL);
			} catch (InterruptedException e) {
				LoggerFactory.getLogger(this.getClass()).error("Could not sleep the message queue", e);
				return;
			}
		}
	}

	@Override
	public long submit(final UserMessage message) throws UserMessageQueueException {
		try {
			monitor.writeLock().lock();
			boolean successful = messageQueue.offer(message);
			if (!successful) {
				throw new UserMessageQueueException("We're over capacity!");
			}
			return messageId.getAndAdd(1);
		} finally {
			monitor.writeLock().unlock();
		}
	}

	public void processQueue() {
		try {
			monitor.readLock().lock();
			logger.info("Serving message queue with {} messages", messageQueue.size());
			for (UserMessage message : messageQueue) {
				message.receiver.worker.sendToClient("Message from " + message.sender.username + ":");
				message.receiver.worker.sendToClient(message.message);
				messageQueue.remove(message);
			}
		} finally {
			monitor.readLock().unlock();
		}
	}

	public UserMessageQueueImpl() {
		messageQueue = new LinkedList<UserMessage>();
	}
}
