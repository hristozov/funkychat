package nl.forkbomb.funkychat.server.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class MessageParserTest {
	private MessageParser parser;

	@Before
	public void setUp() {
		parser = MessageParser.getParser();
	}

	@After
	public void tearDown() {
		parser = null;
	}

	@Test
	public void testWithBye() throws MessageParserException {
		assertEquals(
				new Message(MessageConsts.BYE.getMessage(), new String[0]),
				parser.parseLine(MessageConsts.BYE.getMessage()));
	}

	@Test
	public void testWithSendTo() throws MessageParserException {
		assertEquals(
				new Message(MessageConsts.SEND_TO.getMessage(), new String[]{"dragan", "hello"}),
				parser.parseLine(MessageConsts.SEND_TO.getMessage() + " dragan hello"));
	}

	@Test(expected=MessageParserException.class)
	public void testWithInvalidInput() throws MessageParserException {
		parser.parseLine("azobi4amma4iboza");
	}
	
	@Test
	public void testWithSimpleFileTransfer() throws MessageParserException {
		parser.parseLine(MessageConsts.SEND_FILE_TO.getMessage() + " lol 123");
		parser.parseLine("azobi4amma4iboza");
		parser.parseLine("trololo");
		// end of transfer...
		assertEquals(
				new Message(MessageConsts.BYE.getMessage(), new String[0]),
				parser.parseLine(MessageConsts.BYE.getMessage()));
	}
}
