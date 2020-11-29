import javafx.application.Application;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class AppMain extends Application {
    private GUI gui = new GUI();

    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        primaryStage.setScene(gui.getMainScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        //System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        launch(args);
    }

    /**
     * Method for Primary Stage control
     * @param primaryStage
     */
    private void setStage(Stage primaryStage) {
        primaryStage.setTitle("Camera Car Gruppe 20");
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(800);
    }

}
