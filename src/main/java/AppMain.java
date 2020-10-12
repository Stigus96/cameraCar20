import javafx.application.Application;
import javafx.stage.Stage;

public class AppMain extends Application {
    private GUI gui = new GUI();
    private TCPClient tcpClient = new TCPClient();

    String HOST_NAME = "10.22.178.184";
    int PORT = 1300;

    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        primaryStage.setScene(gui.getMainScene());
        primaryStage.show();
        tcpClient.connect(HOST_NAME, PORT);
    }

    public static void main(String[] args) {
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
