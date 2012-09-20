package nl.forkbomb.funkychat.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.beust.jcommander.JCommander;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		new JCommander(config, args);
		System.out.println(config.host + " " + config.port);

		ExecutorService executorService = Executors.newCachedThreadPool();

		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(config.host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		int port = config.port;
		SocketAddress sockaddr = new InetSocketAddress(addr, port);
		Socket sock = new Socket();
		try {
			sock.connect(sockaddr, 5000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		OutputStream os = null;
		InputStream is = null;
		try {
			os = sock.getOutputStream();
			is = sock.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		executorService.submit(new WriteWorker(os));
		executorService.submit(new ReadWorker(is));
	}
}
