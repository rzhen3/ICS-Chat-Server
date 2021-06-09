package multiclient;

import java.net.Socket;
import java.net.*;
import java.util.*;
import java.io.*;

public class ServerConnection implements Runnable{
	private Socket server;
	private BufferedReader in;
	
	public ServerConnection(Socket s) throws IOException {
		server = s;
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				String serverResponse = in.readLine();
				if(serverResponse == null) break;
				System.out.println("Server says: " + serverResponse);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}

}
