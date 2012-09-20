package nl.forkbomb.funkychat.server.messageHandling.responses;


/**
 * A successful response, which holds a result.
 * @author gh
 */
public class ResultResponse extends AbstractResponse {
	private String[] linesOfResults;

	public ResultResponse(final String[] lines) {
		this.linesOfResults = lines;
	}
	
	public ResultResponse(final String line) {
		this.linesOfResults = new String[]{ line };
	}

	@Override
	public final String[] toListOfLines() {
		String[] result = new String[linesOfResults.length];
		for (int i = 0; i < linesOfResults.length; i++) {
			result[i] = "200 " + linesOfResults[i];
		}
		return result;
	}

	@Override
	public final boolean equals(final Object otherResponse) {
		if (!(otherResponse instanceof ResultResponse)) {
			return false;
		}

		ResultResponse otherResult = (ResultResponse) otherResponse;
		String[] otherLines = otherResult.linesOfResults;

		//XXX: null check
		if (linesOfResults.length != otherLines.length) {
			return false;
		}

		for (int i = 0; i < linesOfResults.length; i++) {
			//XXX: null check
			if (!linesOfResults[i].equals(otherLines[i])) {
				return false;
			}
		}

		return true;
	}
}
