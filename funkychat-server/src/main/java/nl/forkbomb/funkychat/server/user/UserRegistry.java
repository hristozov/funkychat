package nl.forkbomb.funkychat.server.user;

import java.util.Collection;

import com.google.inject.ImplementedBy;

@ImplementedBy(UserRegistryImpl.class)
public interface UserRegistry {
	UserSession registerUser(String username, UserSession session) throws UserRegistryException;
	UserSession registerUser(UserSession session);

	boolean isUserRegistered(String username);

	Collection<UserSession> unRegisterUser(String username) throws UserRegistryException;
	
	String[] getNamesOfRegisteredUsers();
	
	Collection<UserSession> getUser(String username);
	
	int cleanIdleUsers();
}
