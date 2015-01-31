import java.net.*;
import java.util.Vector;
import java.io.*;

import javax.swing.JTextField;

public class piSpeechServer extends Thread {
	private ServerSocket serverSocket;
	int count = 0;
	piSpeechProcessorLocal speechProcessor;
	JTextField console;
	public piSpeechServer(JTextField console, String ipAddress) throws IOException {
		serverSocket = new ServerSocket(6067);
		serverSocket.setSoTimeout(5000000);
		this.console = console;
		speechProcessor = new piSpeechProcessorLocal(console, ipAddress);
	}

	public void run() {
		while (true) {
			try {
				//receive from sender, based on type of send on what it requires
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Connected" + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
				//get from handler
				String type = in.readUTF();
				
				if (type.equals("speech"))
				{
					String input1 = in.readUTF();
					try {
						speechProcessor.processSpeech(input1);
						console.setText(input1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						console.setText(e.toString());
					}
				}
				
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("received");
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				console.setText(e.toString());
				break;
			}
		}
	}
}
