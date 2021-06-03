package multiclient;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class ClientHandler implements Runnable{
	private Socket client;
	private BufferedReader in;
	private  PrintWriter out;
	private ArrayList<ClientHandler> clients;
	
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
		this.client = clientSocket;
		this.clients = clients;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}
	public void run(){
		try {
        	while(true) {
	        	String request = in.readLine();
		        if(request.contains("name"))
		        	out.println(Server.getRandomName());
		        else if(request.startsWith("say")) {
		        	if(request.indexOf(" ")!= -1)
		        		outToAll(request.substring(request.indexOf(" ")+1));
		        	
		        }
		        else {
		        	
		        	out.println("Type 'tell me a name' to get a random name");
		        }
		        System.out.println("Client Input #" + clients.indexOf(this)+": "+request);
		        //adding (this) is the smartest line of code i've written in a month
		        	
	        }
        }catch(IOException e) {
        	System.err.println("IO exception in client handler");
        	 e.printStackTrace();
        }
        finally {
        	try {
				client.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
	        out.close();
        }
	}
	private void outToAll(String substring) {
		// TODO Auto-generated method stub
		for(ClientHandler aClient: clients) {
			aClient.out.println(substring);
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
