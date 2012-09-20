package nl.forkbomb.funkychat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class WriteWorker implements Runnable {
	private BufferedWriter writer;

	public WriteWorker(OutputStream os) {
		writer = new BufferedWriter(new OutputStreamWriter(os));
	}

	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				String line = reader.readLine();
				writer.write(line + "\r\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
