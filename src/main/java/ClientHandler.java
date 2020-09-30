import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private String clientId;
    private final Server server;


    /**
     * @param server the chat server.
     * @param clientSocket the client socket.
     */
    public ClientHandler(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
        this.clientId = "Anonymous" + GlobalCounter.getNumber();
    }

    public void handleIncomingCommands(String cmd){
    if (cmd.startsWith("sensorOnOff")){
        //turn sensor on or off
    }

    if (cmd.startsWith("START")){
        //do something
    }

    }


}