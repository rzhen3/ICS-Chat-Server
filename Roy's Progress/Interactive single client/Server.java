import java.net.*;
import java.util.Date;
import java.io.*;
public class Server {

		private static String[] nicks = {"boy", "the toy", "the boy toy?", "the soyboy", "the koi","el-melloi", "zheng"};
		private static final int PORT = 6666;
		    /**
		     * @param args the command line arguments
		     */
	    public static void main(String[] args) throws IOException {
	        // TODO code application logic here
	        ServerSocket listener = new ServerSocket(PORT);
	        System.out.println("[SERVER] Waiting for client connection...");
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
			        	
			        	out.println("Type 'name'");
			        }
			        System.out.println("User Input:" + request);
			        	
		        }
	        }
	        finally {
	        	client.close();
		        listener.close();
		        out.close();
	        }
	        
	        
	        
	        
	    }
	    
	    public static String getRandomName() {
	    	String nick = nicks[(int)(Math.random()*nicks.length)];
	    	return  "Roy" + nick;
	    }
	

}
