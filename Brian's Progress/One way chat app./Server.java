/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketprogrammingpractice;
import java.net.*;
import java.io.*;


public class Server {
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    
    public Server(int port){
        try{
            server = new ServerSocket(port);
            System.out.println("Server Started");
            
            System.out.println("waiting for a party to join...");
            
            socket = server.accept();
            System.out.println("Client accepted");
            
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            
            String line="";
            
            while(!line.equals("Stop")){
                try{
                    line = in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i){
                    System.out.println(i);
                }
            }
            System.out.println("Server is closing...");
            
            socket.close();
            in.close();
        }
        catch (IOException i){
            System.out.println(i);
        }
    }
}


