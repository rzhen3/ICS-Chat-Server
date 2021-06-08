package multiclient;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class ClientHandler implements Runnable{
	private Socket client;
	private BufferedReader in;
	private  PrintWriter out;
	private ArrayList<ClientHandler> clients;
	//gets the socket from the server so that it can be managed
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
		this.client = clientSocket;
		this.clients = clients;
		//set up streams
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}
	//main run code
	public void run(){
		try {
        	while(true) {
        		//accepting input from client
	        	String request = in.readLine();
	        	//command
		        if(request.contains("name"))
		        	out.println(Server.getRandomName());
		        //server message
		        else if(request.startsWith("say")) {
		        	if(request.indexOf(" ")!= -1)
		        		outToAll(request.substring(request.indexOf(" ")+1));
		        	
		        }
		        else
		        	out.println("Type 'tell me a name' to get a random name");
		        
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
	
	//for each instance of a client, print on their screen the string 'substring'
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
