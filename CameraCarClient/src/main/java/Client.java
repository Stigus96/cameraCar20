import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.xml.bind.DatatypeConverter;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

public class Client {
    private Socket connection;
    private String lastError = null;

    public boolean connect(String host, int port) {
        try {
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

    public synchronized Socket getConnection(){
        return connection;
    }

    public synchronized void disconnect() {
        if( isConnectionActive() )
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
        while ( isConnectionActive() ) {
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
    }

    public boolean sendACommand(String cmd){
        sendCommand(cmd);
        return true;
    }
}
