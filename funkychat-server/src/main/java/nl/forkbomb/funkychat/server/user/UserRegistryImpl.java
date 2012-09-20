package nl.forkbomb.funkychat.server.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


/**
 * Implementation for {@link UserRegistry}.
 * @author gh
 */
public class UserRegistryImpl implements UserRegistry {
	private ReadWriteLock monitor;
	private Multimap<String, UserSession> registeredUsers;

	public static int PING_TIMEOUT = 60 * 1000;

	public static String ANON_ID = "anon";

	public UserRegistryImpl() {
		monitor = new ReentrantReadWriteLock();
		registeredUsers = HashMultimap.create();
	}

	public final UserSession registerUser(final String username, final UserSession session) throws UserRegistryException {
		try {
			monitor.writeLock().lock();
			if (isUserRegistered(username)) {
				throw new UserRegistryException("User already registered");
			}

			if (registeredUsers.containsEntry(ANON_ID, session)) {
				registeredUsers.remove(ANON_ID, session);
			}

			registeredUsers.put(username, session);
			return session;
		} finally {
			monitor.writeLock().unlock();
		}
	}

	public final UserSession registerUser(final UserSession session) {
		try {
			monitor.writeLock().lock();
			registeredUsers.put(ANON_ID, session);
			return session;
		} finally {
			monitor.writeLock().unlock();
		}
	}

	public final boolean isUserRegistered(final String username) {
		try {
			monitor.readLock().lock();
			return registeredUsers.containsKey(username);
		} finally {
			monitor.readLock().unlock();
		}
	}

	public final Collection<UserSession> unRegisterUser(final String username) throws UserRegistryException {
		try {
			monitor.writeLock().lock();
			if (!isUserRegistered(username)) {
				throw new UserRegistryException("User not registered");
			}
			return registeredUsers.removeAll(username);
		} finally {
			monitor.writeLock().unlock();
		}
	}

	public final String[] getNamesOfRegisteredUsers() {
		try {
			monitor.readLock().lock();
			Set<String> keySet = registeredUsers.keySet();
			keySet.remove(ANON_ID);
			return keySet.toArray(new String[0]);
		} finally {
			monitor.readLock().unlock();
		}
	}

	@Override
	public Collection<UserSession> getUser(final String username) {
		try {
			monitor.readLock().lock();
			return registeredUsers.get(username);
		} finally {
			monitor.readLock().unlock();
		}
	}

	@Override
	public int cleanIdleUsers() {
		List<UserSession> sessions;
		try {
			monitor.readLock().lock();
			sessions = new ArrayList<UserSession>(registeredUsers.values());
		} finally {
			monitor.readLock().unlock();
		}

		Collections.sort(sessions, new UserSessionLastContactComparator());
		long currentTime = System.currentTimeMillis();
		int cleanedUsers = 0;
		for (UserSession session : sessions) {
			if (currentTime - session.lastContact < PING_TIMEOUT) {
				break;
			} else {
				try {
					unRegisterUser(session.username);
				} catch (UserRegistryException e) {
					LoggerFactory.getLogger(getClass()).error("Cleanup failed!", e);
				}
				session.worker.stop();
				cleanedUsers++;
			}
		}
		return cleanedUsers;

	}

	private class UserSessionLastContactComparator implements Comparator<UserSession> {
		@Override
		public int compare(UserSession arg0, UserSession arg1) {
			return arg0.lastContact.compareTo(arg1.lastContact);
		}

	}
}
