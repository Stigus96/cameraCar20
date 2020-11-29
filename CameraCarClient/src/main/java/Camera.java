import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Base64;

public class Camera extends Thread{
    private Socket connection;

    public Camera(Socket connection){
        this.connection = connection;
    }

    public void run(){
        while(true){
            getFrame();
        }
    }

    private Mat getFrame() {
        Mat frame = null;
        try {
            frame = new Mat();
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
        }

        return frame;
    }
}
