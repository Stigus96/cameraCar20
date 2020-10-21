import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicBoolean;

public class GUI {
    private GUI_bottom gui_bottom = new GUI_bottom();
    private TCPClient tcpClient;
    private Scene scene;

    String HOST_NAME = "127.0.0.1";
    int PORT = 1300;

    public GUI(){
        setMainScene();
        tcpClient = new TCPClient();
        tcpClient.connect(HOST_NAME, PORT);
    }

    public Scene getMainScene(){
        return this.scene;
    }

    /**
     * Set the main scene
     */
    private void setMainScene() {
        this.scene = new Scene(mainContainer());
        this.scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Login window
     * Username and password required to get to mainContainer
     * @return
     */
    private VBox loginWindow(){
        return new VBox();
    }

    /**
     * Main Container
     * @return
     */
    private VBox mainContainer(){
        return new VBox(topBox(), gui_bottom.getBottomPane());
    }

    private HBox topBox(){
        HBox hBox = new HBox(leftPane(), buttonControls());
        hBox.setMinHeight(400);
        return hBox;
    }

    // Video pane
    private Pane leftPane(){
        Pane pane = new Pane();
        pane.setMinWidth(800);
        pane.setMinHeight(600);
        return pane;
    }

    /**
     * Control buttons top right in the GUI
     * Has start button, stop button, button to toggle sensors
     * @return
     */
    private VBox buttonControls(){
        VBox vBox = new VBox();

        vBox.setSpacing(15);
        vBox.setPadding(new Insets(30,20,30,20));
        vBox.getChildren().addAll(startButton(), stopButton(), sensorButton(), forward());
        return vBox;
    }

    /**
     * button to send command start to server from client
     * @return
     */
    private Button startButton(){
        Button startBtn = new Button("START");
        startBtn.setMaxWidth(Double.MAX_VALUE);
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("starting car");
                if (tcpClient.isConnectionActive()) {
                    tcpClient.sendACommand("START");
                } else {
                    System.out.println("no connection");
                }
            }
        });
        return startBtn;
    }

    /**
     * button to send command stop to server
     * @return
     */
    private Button stopButton(){
        Button stopBtn = new Button("STOP");
        stopBtn.setMaxWidth(Double.MAX_VALUE);
        stopBtn.setOnAction(actionEvent -> {
           if(tcpClient.isConnectionActive()){
               tcpClient.sendACommand("STOP");
           } else {
               System.out.println("no connection");
           }
        });
        return stopBtn;
    }

    /**
     * button to toggle distance sensors on and off
     * @return
     */
    private ToggleButton sensorButton(){
        ToggleButton sensorButton = new ToggleButton("Sensors on/off");
        sensorButton.setMaxWidth(Double.MAX_VALUE);
        sensorButton.setSelected(false);

        sensorButton.setOnAction(actionEvent -> {
            if(sensorButton.isSelected()){
                tcpClient.sendACommand("SENSOR_OFF");
            } else {
                tcpClient.sendACommand("SENSOR_ON");
            }
        });

        return sensorButton;
    }

    private Button forward(){
        Button fwd = new Button("Forward");
        fwd.setMaxWidth(Double.MAX_VALUE);

        fwd.setOnMousePressed(e -> {
            try {
                Thread.sleep(1000);
                System.out.println("Mouse pressed");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        return fwd;
    }

}