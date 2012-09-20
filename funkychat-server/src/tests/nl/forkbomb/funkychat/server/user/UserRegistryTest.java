package nl.forkbomb.funkychat.server.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UserRegistryTest {
	private UserRegistry testRegistry;
	
	@Before
	public void setUp() {
		testRegistry = new UserRegistryImpl();
	}
	
	@After
	public void tearDown() {
		testRegistry = null;
	}
	
	@Test
	public void testUserAddition() throws UserRegistryException {
		testRegistry.registerUser("pesho", UserSession.getUserSession());
	}
	
	@Test(expected=UserRegistryException.class)
	public void testExceptionsInDoubleRegistration() throws UserRegistryException {
		testRegistry.registerUser("petko", UserSession.getUserSession());
		testRegistry.registerUser("petko", UserSession.getUserSession());
	}
	
	@Test(expected=UserRegistryException.class)
	public void testExceptionWhenRemovingNonExistantUser() throws UserRegistryException {
		testRegistry.unRegisterUser("petko");
	}
}
