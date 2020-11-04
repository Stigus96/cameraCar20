import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Handle client connection. This method is entry point when a new thread
     * is started
     */
    @Override
    public void run(){
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;

            try {
                // Read from socket until it closes.
                while ((line = input.readLine()) != null){
                    handleIncomingCommands(line);
                }
                // Socket closed on the client side, force closing the server side as well
                clientSocket.close();
            }
            catch (SocketException ex)
            {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Socket has been closed. Remove user from connectedClients.
        server.removeConnectedClient((int)Thread.currentThread().getId());
    }

    public void handleIncomingCommands(String cmd) {
        if (cmd.trim().equals("sensorOnOff")) {
            //turn sensor on or off
        }

        if (cmd.trim().equals("START")) {
            System.out.println("starting stuff");
        } else {
            //command not supported
            send("cmderr command not supported");
        }
    }

    public void send(String msg) {
        try {
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
            pw.println(msg);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}