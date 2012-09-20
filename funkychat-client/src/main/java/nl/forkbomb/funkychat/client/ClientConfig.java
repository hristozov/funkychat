package nl.forkbomb.funkychat.client;

import com.beust.jcommander.Parameter;

public class ClientConfig {
	@Parameter(names="--port", description="Port to connect to", required=true)
	public int port;
	
	@Parameter(names="--host", description="Host to connect to", required=true)
	public String host;
}
