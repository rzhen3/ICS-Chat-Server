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
import java.util.logging.Level;
import java.util.logging.Logger;
public class Client implements Runnable{
    private String SERVER_IP = "127.0.0.1";
    private int SERVER_PORT = 9999;
    public Socket clientSocket;
    public ServerOutput serverOut;
    //Standard direct join constructor
    public Client(String IP, String PORT){
        this.SERVER_IP = IP;
        this.SERVER_PORT = Integer.parseInt(PORT);
    }
    //Overloaded joining via code constructor
    public Client(String code){
        String joinCode = Server.deCipher(code, Server.getCipher());
        SERVER_IP = joinCode.substring(0, 9);
        SERVER_PORT = Integer.parseInt(joinCode.substring(10));
    }
    @Override
    public void run(){
        try{
            //establishing server connection and initializing output and keyboard input
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("[CLIENT] Connected to server");
            PrintWriter dataOut = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            serverOut = new ServerOutput(clientSocket);
            new Thread(serverOut).start();
            
            //reading input and sending to server
            while(true){
                String cInput = keyboard.readLine();
                dataOut.println(cInput);
            }
        }catch(IOException e){
            System.err.println("Error starting or connecting to the server");
        }

    }
}
