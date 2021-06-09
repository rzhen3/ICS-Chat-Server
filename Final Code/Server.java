/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
/**
 *
 * @author Roy Zheng
 */
public class Server {
    private static final int PORT = 5678;
    private static final String IP = "127.0.0.1";
    private static ArrayList<ClientHandler> clientList = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4); //thread pool
    
    public static void main(String[] args) throws IOException{
        
        ServerSocket listener = new ServerSocket(5678);//standard wait for client connection
        System.out.println("---------------------------------- S E R V E R     S I D E ----------------------------------");
        System.out.println("[SYSTEM] Starting server");
        
        while(true){
            //Wait for client connections and add to clientList and thread pool
            System.out.println("[SYSTEM] Waiting for client connection...");
            Socket newClient = listener.accept();
            System.out.println("[SYSTEM] New client connection");
            ClientHandler newClientThread = new ClientHandler(newClient, clientList);
            clientList.add(newClientThread);
            pool.execute(newClientThread);
            
        }
        
        
        
    }
}
