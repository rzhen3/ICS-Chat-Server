package finalproject;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Roy Zheng
 */
public class Server implements Runnable{
    private static int PORT = 9999;
    private static String IP = "127.0.0.1";
    public static String code;
    private static int[] cipher;
    public static ArrayList<ClientHandler> clientList = new ArrayList<>();//Server.clientList.userName
    public static volatile ExecutorService pool = Executors.newFixedThreadPool(4); //thread pool
    public ServerSocket listener;
    public static File exportFile;
    public static FileOutputStream exporter;
    //initializing necessary server attributes
    public Server (String code, int[] cipher){
        this.code = code;
        this.cipher = cipher;
        //set up file to export to while handling duplicates
        try{
            exportFile = new File("Chat Log.txt");
            int item = 1;
            while(!(exportFile.createNewFile())){
                exportFile = new File("Chat Log ("+item+").txt");
                item++;
            }
            //initialize
            exporter = new FileOutputStream(exportFile);
        }catch(IOException e){
            System.out.println("An error occurred while creating the export file");
        }
    }
    //getters for encapsulation
    public static int[] getCipher(){
        return cipher;
    }
    public static int getPORT(){
        return PORT;
    }
    public static String getIP(){
        return IP;
    }
        
    //deciphering a code string using a cipherstream 
    public static String deCipher(String code, int[] cipherStream){
        char[] newCode = code.toCharArray();
        String deCipheredCode = "";
        //apply decryption translations
        for(int i = 0;i<code.length();i++){
            deCipheredCode +=(char)(newCode[i]-cipherStream[i]);
        }
        return deCipheredCode;
    }
    
    @Override
    public void run() {
        try{
            listener = new ServerSocket(PORT);//standard wait for client connection
            write("---------------------------------- S E R V E R     S I D E ----------------------------------", this.exporter);
            write("[SERVER] Starting server", this.exporter);
            while(true){
                //Wait for client connections and add to clientList and thread pool
                write("[SERVER] Waiting for new client connection...", this.exporter);
                Socket newClient = listener.accept();
                write("[SERVER] New client connection", this.exporter);
                ClientHandler newClientThread = new ClientHandler(newClient);
                clientList.add(newClientThread);
                pool.execute(newClientThread);
            }
        }
        //handling errors that may occur with server starting or connection
        catch(BindException e){
            System.err.println("Server already exists on specified localport.");
            return;
        }catch(IOException ee){
            System.err.println("Error starting or connecting to the server.");
        }catch(Exception eee){
            System.err.println("An unknown error occurred.");
        }
    }
    //prints to server console and writes to file
    public static void write(String message, FileOutputStream exporter){
        System.out.println(message);
        try{
            char[] script = message.toCharArray();
            for(char c:script) {
                exporter.write(c);
            }
            exporter.write(System.getProperty("line.separator").getBytes());
        }catch(IOException e){
            System.err.println("[ERROR] Error writing to file");
        }
    }
}
