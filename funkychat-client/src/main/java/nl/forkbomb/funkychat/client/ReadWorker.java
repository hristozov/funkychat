package nl.forkbomb.funkychat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadWorker implements Runnable {
	BufferedReader reader;
	
	
	public ReadWorker(InputStream is) {
		reader = new BufferedReader(new InputStreamReader(is));
	}
	
	public void run() {
		while(true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(line);
		}
	}

}
