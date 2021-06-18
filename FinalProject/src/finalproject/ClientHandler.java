package finalproject;

/**
 * ClientHandler class handles all input that comes into the server
 * @author Roy Zheng
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{
    private Socket serverSocket;
    BufferedReader dataIn;
    PrintWriter dataOut;
    public String userName;
    
    //setting socket and initializing i/o streams
    public ClientHandler(Socket serverSocket)throws IOException{
        this.serverSocket = serverSocket;
        dataIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));//Connects to Client.socket.dataOut
        dataOut = new PrintWriter(serverSocket.getOutputStream(), true);//Connects to Client.socket.dataIn
    }
    
    public void run(){
        try {
            System.out.println("I AM "+serverSocket.toString());
            userName = "Client #"+(Server.clientList.indexOf(this));
            while(true) {
                String request = dataIn.readLine();
                if(request.startsWith("!")){
                    String command;
                    //Parameterized command
                    if(request.indexOf(" ")==-1)
                        command = request.substring(request.indexOf("!")+1);
                    //standard command
                    else 
                        command = request.substring(request.indexOf("!")+1, request.indexOf(" "));
                    boolean isValid=false;
                    for(Commands c:Commands.values()){
                        if(c.name().equalsIgnoreCase(command))
                            isValid = true;
                    }
                    //check for command validity
                    if(isValid){
                        //notify
                        Server.write("[SYSTEM] Client "+Server.clientList.indexOf(this)+ " executed a command \'"+command+"\'", Server.exporter);
                        this.dataOut.println("@"+"[SYSTEM] Client "+Server.clientList.indexOf(this)+ " executed a command \'"+command+"\'");
                        //execute command code
                        switch(command.toLowerCase()){
                            case "change_name":
                                userName = request.substring(request.indexOf(" ")+1);
                                Server.write("[SYSTEM] Username changed", Server.exporter);
                                this.dataOut.println("@<SYSTEM>: Username changed");
                                break;
                            case "instructions":
                                this.dataOut.println("@--- I N S T R U C T I O N S ---\n"
                                        + "@This area is the text display box. \n"
                                        + "@Messages are displayed following a format:\n"
                                        + "@\"Date\"\n"
                                        + "@\"Username\": *message*\n"
                                        + "@You can use commands by typing \'!\' followed by the command.\n"
                                        + "@Additional input may be required. \n"
                                        + "@Type what you want to say in the message display box. \n"
                                        + "@To send, press send. \n"
                                        + "@You may leave with quit. \n"
                                        + "@Good luck!?");
                                break;
                            case "commands":
                                this.dataOut.println("@<SYSTEM>: --- C O M M A N D S ---\n"+
                                         "@Here are all the available commands:");
                                for(Commands c:Commands.values()){
                                    this.dataOut.println("@          !"+c.name());
                                }
                                break;
                            case "all":
                                this.dataOut.println("@Active Clients:");
                                for(ClientHandler c:Server.clientList){
                                    this.dataOut.println("@"+c.userName);
                                }
                                break;
                        }
                    }
                    else{
                        //notify failure
                        Server.write("[SYSTEM] Client "+Server.clientList.indexOf(this)+ " failed to execute a command\'"+command+"\'", Server.exporter);
                        this.dataOut.println("@"+"[SYSTEM] Client "+Server.clientList.indexOf(this)+ " failed to execute a command\'"+command+"\'");
                    }
                }
                //not a command = standard message
                else
                    outToAll(userName, request);
                Server.write("Client Input #" + Server.clientList.indexOf(this)+": "+request, Server.exporter);
            }
        //handling client disconnects
        }catch(IOException e) {
            Server.write("[SYSTEM] Client #"+Server.clientList.indexOf(this)+" disconnected.", Server.exporter);
        }catch(NullPointerException ee){
            Server.write("[SYSTEM] Client #"+Server.clientList.indexOf(this)+" disconnected.", Server.exporter);
        }
        finally {
            close();
        }
    }
    //closing all streams, sockets and file-related objects
    private void close(){
        try {
            System.out.println(Server.pool.toString());
            this.serverSocket.close();
            System.out.println(Server.pool.toString());
            this.dataIn.close();
            this.dataOut.close();
            Server.clientList.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //output messge to all clients
    public void outToAll(String userName, String message) {
        if(message.trim().isEmpty())
            return;
        //for each instance of a client, print on their screen the string 'substring'
        for(ClientHandler aClient: Server.clientList) {
            if(aClient.equals(this))
                aClient.dataOut.println("ME: "+message);
            else
                aClient.dataOut.println(userName+": "+message);
        }
    }
}
//all available commands
enum Commands{
    CHANGE_NAME, INSTRUCTIONS, COMMANDS, ALL
}