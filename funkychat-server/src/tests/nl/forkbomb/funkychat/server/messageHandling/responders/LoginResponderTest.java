package nl.forkbomb.funkychat.server.messageHandling.responders;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import nl.forkbomb.funkychat.server.messageHandling.responses.ErrorResponse;
import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserRegistryImpl;
import nl.forkbomb.funkychat.server.user.UserSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link LoginResponder}
 * @author gh
 */
public class LoginResponderTest {
	private UserRegistry userRegistry;
	private LoginResponder loginResponder;
	private UserSession context;

	@Before
	public void setUp() {
		userRegistry = new UserRegistryImpl();
		loginResponder = new LoginResponder(userRegistry);
		context = UserSession.getUserSession();
	}

	@After
	public void tearDown() {
		userRegistry = null;
		loginResponder = null;
		context = null;
	}

	/**
	 * Checks if the user can log in.
	 */
	@Test
	public void testUserLogin() {
		Response actualResponse = loginResponder.handleAction(new Message("login", new String[]{"pencho"}), context);
		Response expectedResponse = new ResultResponse("ok pencho successfully registered!");
		assertTrue(userRegistry.isUserRegistered("pencho"));
		assertEquals(expectedResponse, actualResponse);
	}
	
	/**
	 * Checks if the user gets an error when trying to do double login.
	 */
	@Test
	public void testDoubleLogin() {
		loginResponder.handleAction(new Message("login", new String[]{"pencho"}), context);
		Response expectedResponse = new ErrorResponse("err pencho already taken!");
		Response actualResponse = loginResponder.handleAction(new Message("login", new String[]{"pencho"}), context);
		assertEquals(expectedResponse, actualResponse);
	}
}
