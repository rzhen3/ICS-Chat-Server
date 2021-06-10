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
//to do list: handle socket reset
public class ClientHandler implements Runnable{
    private static Socket clientSocket;
    public static ArrayList<ClientHandler> clientList;
    BufferedReader dataIn;
    PrintWriter dataOut;
    
    public String userName;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientList)throws IOException{
        this.clientSocket = clientSocket;
        this.clientList = clientList;
        
        //Set up streams for receiving and sending data
        dataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//Connects to Client.socket.dataOut
        dataOut = new PrintWriter(clientSocket.getOutputStream(), true);//Connects to Client.socket.dataIn
        
        
    }
    
    public void run(){
        try {
                userName = "Client #"+this.clientList.indexOf(this);
        	while(true) {
                    
        	//accepting input from client
                    String request = dataIn.readLine();
	        //command
                    if(request.startsWith("!")){
                        String command = request.substring(request.indexOf("!")+1, request.indexOf(" "));
                        boolean isValid=false;
                        for(Commands c:Commands.values()){
                            if(c.name().equalsIgnoreCase(command)){
                                isValid = true;                                        
                            }
                        }
                        if(isValid){
                            System.out.println("Client "+clientList.indexOf(this)+ " executed a command \'"+command+"\'");//temp
                            switch(command.toLowerCase()){
                                case "change_name":
                                    //change username
                                    userName = request.substring(request.indexOf(" ")+1);
                                    System.out.println("CHANGED");
                                    break;
                            }
                        }
                        else{
                            System.out.println("Client "+clientList.indexOf(this)+ " failed to execute a command\'"+command+"\'");//temp
                        }
                    }
                    else{
                        outToAll(userName, request);
                    }   
		    

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
    private void outToAll(String userName, String message) {
        if(message.trim().isEmpty())
            return;
        for(ClientHandler aClient: clientList) {
            if(aClient.equals(this))
                aClient.dataOut.println("ME: "+message);
            else{
                aClient.dataOut.println(userName+": "+message);
            }
        }

    }
    
}
enum Commands{
    CHANGE_NAME
}