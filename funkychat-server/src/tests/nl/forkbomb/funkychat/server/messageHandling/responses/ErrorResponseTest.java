package nl.forkbomb.funkychat.server.messageHandling.responses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Tests for {@link ErrorResponse}
 * @author gh
 */
public class ErrorResponseTest {
	/**
	 * Basic tests for the equals() method.
	 */
	@Test
	public void testEquals() {
		ErrorResponse muncho = new ErrorResponse("muncho");
		assertEquals(muncho, new ErrorResponse("muncho"));
		assertFalse(muncho.equals(null));
		assertFalse(muncho.equals(new ErrorResponse("jajaja")));
		assertFalse(muncho.equals(new ResultResponse("lololo")));
		assertFalse(muncho.equals(new ErrorResponse(null)));
	}
}
