package nl.forkbomb.funkychat.server.messageHandling.responses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Tests for {@link ResultResponse}
 * @author gh
 */
public class ResultResponseTest {
	/**
	 * Basic tests for the equals() method.
	 */
	@Test
	public void testEquals() {
		ResultResponse muncho = new ResultResponse("muncho");
		assertEquals(muncho, new ResultResponse("muncho"));
		assertFalse(muncho.equals(null));
		assertFalse(muncho.equals(new ErrorResponse("jajaja")));
		assertFalse(muncho.equals(new ResultResponse("lololo")));
		assertFalse(muncho.equals(new ResultResponse((String)null)));
	}
	
	@Test
	public void testEqualsForArrays() {
		ResultResponse muncho = new ResultResponse(new String[]{"mun", "cho"});
		assertEquals(muncho, new ResultResponse(new String[]{"mun", "ch" + "o"}));
		assertFalse(muncho.equals(new ResultResponse("boo")));
		assertFalse(muncho.equals(new ResultResponse(new String[]{"a", "b"})));
	}
}
