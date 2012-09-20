package nl.forkbomb.funkychat.server.messageHandling.responders;

import static junit.framework.Assert.assertEquals;
import nl.forkbomb.funkychat.server.messageHandling.responses.Response;
import nl.forkbomb.funkychat.server.messageHandling.responses.ResultResponse;
import nl.forkbomb.funkychat.server.messages.Message;
import nl.forkbomb.funkychat.server.user.UserRegistry;
import nl.forkbomb.funkychat.server.user.UserRegistryException;
import nl.forkbomb.funkychat.server.user.UserRegistryImpl;
import nl.forkbomb.funkychat.server.user.UserSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListResponderTest {
	private UserRegistry userRegistry;
	private ListResponder loginResponder;
	private UserSession context;

	@Before
	public void setUp() {
		userRegistry = new UserRegistryImpl();
		loginResponder = new ListResponder(userRegistry);
		context = UserSession.getUserSession();
	}

	@After
	public void tearDown() {
		userRegistry = null;
		loginResponder = null;
		context = null;
	}

	@Test
	public void testWithEmptyList() {
		Response response = loginResponder.handleAction(new Message("list"), context);
		assertEquals( new ResultResponse(new String[0]), response);
	}
	
	@Test
	public void testWithOneUser() throws UserRegistryException {
		userRegistry.registerUser("peko", UserSession.getUserSession());
		Response response = loginResponder.handleAction(new Message("list"), context);
		assertEquals(new ResultResponse("peko"), response);
	}
}
