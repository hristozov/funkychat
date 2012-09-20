package nl.forkbomb.funkychat.server.messageHandling.responses;

public class ErrorResponse extends AbstractResponse {
	private String message;
	
	public ErrorResponse(final String message) {
		this.message = message;
	}

	@Override
	public final String[] toListOfLines() {
		String[] result = new String[1];
		result[0] = "100 " + message;
		return result;
	}
	
	@Override
	public final boolean equals(final Object otherResponse) {
		if (!(otherResponse instanceof ErrorResponse)) {
			return false;
		}

		ErrorResponse otherError = (ErrorResponse) otherResponse;
		//XXX: null check
		return message.equals(otherError.message);
	}

}
