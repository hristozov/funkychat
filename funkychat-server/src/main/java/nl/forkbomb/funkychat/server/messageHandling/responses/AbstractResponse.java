package nl.forkbomb.funkychat.server.messageHandling.responses;

import org.apache.commons.lang3.StringUtils;

public class AbstractResponse implements Response {

	@Override
	public String[] toListOfLines() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return StringUtils.join(toListOfLines(), ' ');
	}

}
