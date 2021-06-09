/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 * ClientHandler class handles all input that comes into the server
 * @author Roy Zheng
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{
    private static Socket clientSocket;
    public static ArrayList<ClientHandler> clientList;
    BufferedReader dataIn;
    PrintWriter dataOut;
    
    
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientList)throws IOException{
        this.clientSocket = clientSocket;
        this.clientList = clientList;
        
        //Set up streams for receiving and sending data
        dataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//Connects to Client.socket.dataOut
        dataOut = new PrintWriter(clientSocket.getOutputStream(), true);//Connects to Client.socket.dataIn
    }
    
    public void run(){
        try {
        	while(true) {
                    
        	//accepting input from client
                    String request = dataIn.readLine();
	        	//command
		    outToAll(request);

		    System.out.println("Client Input #" + clientList.indexOf(this)+": "+request);
		        //adding (this) is the smartest line of code i've written in a month
		        	
	        }
        }catch(IOException e) {
        	System.err.println("IO exception in client handler");
        	 e.printStackTrace();
        }
        finally {
        	try {
                    clientSocket.close();
                    dataIn.close();
                    dataOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
                    e.printStackTrace();
		}
        }
    }
    //for each instance of a client, print on their screen the string 'substring'
    private void outToAll(String message) {
        for(ClientHandler aClient: clientList) {
            if(aClient.equals(this))
                aClient.dataOut.println("ME: "+message);
            else
                aClient.dataOut.println("User "+ clientList.indexOf(this)+": "+message);
            
        }

    }
    
}
