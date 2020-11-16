import java.net.*;
import java.io.*;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

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
            byte[] video = new byte[64];
            in.read(video);
            frame = Imgcodecs.imdecode(new MatOfByte(video), Imgcodecs.IMREAD_UNCHANGED);
            return frame;
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
