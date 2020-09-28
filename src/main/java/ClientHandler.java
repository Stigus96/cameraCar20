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

}