/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 *
 * @author Roy Zheng
 */
import java.io.*;
import java.net.*;
import java.util.*;
public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 5678;
    public static Socket serverSocket;
    public static void main(String[] args)throws IOException{
        //establishing server connection
        serverSocket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("[CLIENT] Connected to server");
        
        //connect this socket outputstream 
        PrintWriter dataOut = new PrintWriter(serverSocket.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        
        //create thread to handle printing the server output
        ServerOutput serverOut = new ServerOutput(serverSocket);
        new Thread(serverOut).start();
        
        while(true){
            //reading input from keyboard
            String cInput = keyboard.readLine();
            if(cInput.equalsIgnoreCase("quit")) break;
            
            //send client input to server socket
            dataOut.println(cInput);
        }
        serverSocket.close();
    }
}
