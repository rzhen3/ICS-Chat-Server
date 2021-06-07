package multiclient;
import java.net.*;
import java.util.Date;
import java.io.*;
public class Server {

		private static String[] names = {"Wily", "Felix", "Carlsbad", "Hobob"};
		private static String[] adjs = {"the gentle", "the un-gentle", "the overwrought", "the urbane"};
		private static final int PORT = 9090;
		    /**
		     * @param args the command line arguments
		     */
	    public static void main(String[] args) throws IOException {
	        // TODO code application logic here
	        ServerSocket listener = new ServerSocket(PORT);
	        System.out.println("{SERVER] Waiting for client connection...");
	        //new socket for a client
	        Socket client = listener.accept();
	        System.out.println("[SERVER] Connected to client!");
	        //connect output to the socket outputstream
	        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        try {
	        	while(true) {
		        	String request = in.readLine();
			        if(request.contains("name"))
			        	out.println(getRandomName());
			        else {
			        	
			        	out.println("Type 'tell me a name' to get a random name");
			        }
			        System.out.println("Client Input:" + request);
			        	
		        }
	        }
	        finally {
	        	client.close();
		        listener.close();
		        out.close();
	        }
	        
	        
	        //System.out.println("[SERVER] Name sent. Closing");
	        
	        
	    }
	    
	    public static String getRandomName() {
	    	String name = names[(int)(Math.random()*names.length)];
	    	String adj = adjs[(int)(Math.random()*adjs.length)];
	    	return name + " " + adj;
	    }
	

}
