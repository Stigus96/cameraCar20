
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Camera {

private VideoCapture capture = new VideoCapture();
private Timer timer = new Timer();



if (!capture.isOpened()) {
    System.out.print("Did not connect camera");
} else {System.out.print("found webcam " + capture.toString());}


}
