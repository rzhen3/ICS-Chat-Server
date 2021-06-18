package finalproject;

/**
 * ServerOutput class handles the output that the server provides
 * @author Roy Zheng
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerOutput implements Runnable {
    private Socket clientSocket;
    private BufferedReader dataIn;

    public messagingScreen myMS;
    //setting socket and input stream and initializing messaging screen
    public ServerOutput(Socket serverSocket)throws IOException{
        this.clientSocket = serverSocket;
        dataIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        myMS = new messagingScreen();
        myMS.setClient(serverSocket);
        myMS.setVisible(true);
    }
    @Override 
    public void run(){
        try {
            while(true) {
                    String serverResponse = dataIn.readLine();
                    //check for special formatting
                    if(serverResponse.startsWith("@"))
                        myMS.printMessage(serverResponse.substring(serverResponse.indexOf("@")+1));
                    //standard message with username and message
                    else{
                        String uName = serverResponse.substring(0, serverResponse.indexOf(":"));
                        String message = serverResponse.substring(serverResponse.indexOf(":")+1);
                        myMS.printMessage(uName, message);
                    }
                    
            }

        } 
        //handle leaving
        catch (IOException e) {
            System.err.println("[CLIENT] Server connection interrupted");
            myMS.printMessage("<SYSTEM>: A client has left.");
        }
        //socket stuff
        finally {
            try {
                clientSocket.close();
                dataIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
