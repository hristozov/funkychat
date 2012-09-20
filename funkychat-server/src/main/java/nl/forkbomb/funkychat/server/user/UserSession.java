package nl.forkbomb.funkychat.server.user;

import nl.forkbomb.funkychat.server.worker.Worker;

public class UserSession {
	public String username;
	public Worker worker;
	public Long lastContact;
	
	public static UserSession getUserSession() {
		return getUserSession(null);
	}
	
	public static UserSession getUserSession(Worker worker) {
		return new UserSession(worker);
	}
		
	private UserSession() {
		
	}
	
	private UserSession(Worker worker) {
		this.worker = worker;
	}
}
