/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketprogrammingpractice;
import java.net.*;
import java.io.*;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private DataOutputStream out;

    public Client (String add, int port){
        try{
            socket = new Socket(add, port);
            System.out.println("Connected");
            
            input = new BufferedReader(new InputStreamReader(System.in));
            
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch (IOException i){
            System.out.println(i);
        }
        
        String userLine = "";
        
        while(! userLine.equals("Stop"))
        {
            try{
                userLine = input.readLine();
                out.writeUTF(userLine);
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        
        try{
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i){
        }
    }
    
    public static void main(String[] args) {
       Client client = new Client("127.0.0.1",9090);
    }
    
}
