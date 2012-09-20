package nl.forkbomb.funkychat.server.user;

import java.util.TimerTask;

import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Task responsible for disconnecting the users that haven't been active for some period of time.
 * Uses {@link UserRegistry#cleanIdleUsers()}.
 * @author gh
 */
public class UserRegistryCleaner extends TimerTask {
	@Inject private UserRegistry userRegistry;

	@Override
	public void run() {
		try {
			LoggerFactory.getLogger(this.getClass()).info("Cleaned up " + userRegistry.cleanIdleUsers() +
					" idle users");
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Cleanup failed", e);
		}
	}

}
