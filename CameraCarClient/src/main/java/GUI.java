import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
        vBox.getChildren().addAll(startButton(), stopButton(), sensorButton(), controlPad(100));
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
            gui_bottom.setSpeed(0);
            gui_bottom.setDircetion(0);
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

    private Circle controlPad(int radius){
        Circle circle = new Circle(radius);

        try {
            String path = "src/main/java/hringur3.jpg";
            FileInputStream circleDesign3 = new FileInputStream(path);
            Image image = new Image(circleDesign3);
            circle.setFill(new ImagePattern(image));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controlVector(mouseEvent.getX(),mouseEvent.getY(), radius);
            }
        });

        return circle;
    }

    private void controlVector(double x, double y, int radius){
        double hypotenus = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0));
        double angle = Math.toDegrees(Math.asin(y/hypotenus)) + 90;

        double speed = 1 - ((radius - hypotenus) / radius);

        angle = setAngle(x, angle);

        tcpClient.sendACommand("VECTOR " + speed + " " + angle);
        gui_bottom.setSpeed(speed);
        gui_bottom.setDircetion(angle);

    }

    private double setAngle(double x, double angle) {
        if(x <= 0){
            angle *= -1;
        }
        return angle;
    }

} // END OF CLASS