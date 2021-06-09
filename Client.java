package multiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import javax.swing.JOptionPane;
public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    
    public static void main(String[] args) throws IOException{
        //establishing server connection
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("[CLIENT] Connected to server");
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String serverResponse = input.readLine();
        JOptionPane.showMessageDialog( null, serverResponse);
        System.out.println(serverResponse);
        socket.close();
    }
}