/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 * ServerOutput class handles the output that the server provides
 * @author Roy Zheng
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerOutput implements Runnable {
    private Socket serverSocket;
    private BufferedReader dataIn;

    
    public ServerOutput(Socket serverSocket)throws IOException{
        //listen to server socket ouput by connecting to the client socket input stream
        this.serverSocket = serverSocket;
        dataIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    }
    @Override 
    public void run(){
        try {
            while(true) {
                    //read client inputstream for server response and show
                    String serverResponse = dataIn.readLine();
                    
                    if(serverResponse == null) break; //indicates that the server has removed the user
                    
                    System.out.println(serverResponse);
            }

        } 
        catch (IOException e) {
            System.err.println("[CLIENT] Server connection interrupted");
        }
        finally {
            try {
                serverSocket.close();
                dataIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
