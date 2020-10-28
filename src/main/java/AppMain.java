
import javafx.application.Application;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.opencv.core.Core;

public class AppMain {
    //Default TCP port to use
    private static final int DEFAULT_PORT = 1300;
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) {

        OpenCV.loadShared();

        //Start new server on a given port
        int port = DEFAULT_PORT;
        if(args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port specified: " + args[0]);
            }
        }
        Server server = new Server();
        server.start(port);


    }



}
