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

public class TCPClient {
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
            waitServerResponse();

        return true;
    }

    public Mat getFrame() {
        Mat frame = new Mat();
        try {
            connection.setSoTimeout(5000);
            InputStream in = connection.getInputStream();

           // System.out.print(length);

            byte[] video = new byte[85536];
            in.read(video);
            //System.out.println(new String (video));
            String x = new String(video);
            x = x.trim();
            System.out.println(x);

            byte[] decodedVideo = Base64.getDecoder().decode(x);
            //System.out.println("video string: " + new String(decodedVideo));
            //in.read(video, 0, 64);
            frame = Imgcodecs.imdecode(new MatOfByte(decodedVideo), Imgcodecs.IMREAD_UNCHANGED);
        }catch (IOException e){
            System.out.println("Socket error: " + e.getMessage());
            lastError = "" + e;
        }

        return frame;
    }

    /**
     * Wait for chat server's response
     *
     * @return one line of text (one command) received from the server
     */
    private String waitServerResponse() {

        try {
            connection.setSoTimeout(2000);
            InputStream in =  connection.getInputStream();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            byte[] data = new byte[100];
            int count = in.read(data);

            String responseLine;
            String serverResponse;

            do{
                responseLine = new String(data);
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
