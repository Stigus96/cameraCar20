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

    public synchronized void disconnect() {
        // TODO Step 4: implement this method -Done
        // Hint: remember to check if connection is active
        if(isConnectionActive())
            try {
                connection.close();
                onDisconnect();
                connection = null;
            }
            catch (IOException e) {
                System.out.println("Socket error: " + e.getMessage());
                lastError = "" + e;
            }

    }

    public boolean isConnectionActive() {
        return connection != null;
    }

    private boolean sendCommand(String cmd) {
        while (isConnectionActive()) {
            try {

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    PrintWriter writer = new PrintWriter(out, true);
                    writer.println(cmd);

                    System.out.println("command sent: " + cmd);

                    return true;


            }  catch (IOException e) {
                System.out.println("Socket error: " + e.getMessage());
                lastError = "" + e;
                return false;
            }
        }
        return true;
    }

    private void onDisconnect() {
        // TODO Implement this method
        }

        public boolean sendACommand(String cmd){
        sendCommand(cmd);
        waitServerResponse();
        return true;
        }

    /**
     * Wait for chat server's response
     *
     * @return one line of text (one command) received from the server
     */
    private String waitServerResponse() {

        try {
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String responseLine;
            String serverResponse;

            do{
                responseLine = reader.readLine();
                if (responseLine != null){
                    System.out.println("SERVER: " + responseLine);
                    serverResponse = responseLine;
                    return serverResponse;
                } else { System.out.println("no response from server");}
            } while (responseLine != null);

        }
        catch (IOException e) {
            System.out.println("Socket error: " + e.getMessage());
            lastError = "" + e;
        }
        return null;
    }

}