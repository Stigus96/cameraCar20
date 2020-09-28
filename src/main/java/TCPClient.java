import java.net.*;
import java.io.*;

public class TCPClient {
    private Socket connection;

    private String lastError = null;

    //test

    public boolean connect(String host, int port) {
        try{
            Socket socket = new Socket(host, port);
            System.out.println("Successfully connected");

            connection = socket;

        } catch (IOException e) {
            System.out.println("Socket error: " + e.getMessage());
            lastError = "" + e;
            return false;
        }
        return true;
    }
}
