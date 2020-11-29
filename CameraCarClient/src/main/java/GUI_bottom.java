import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI_bottom extends SplitPane {

    private SplitPane bottomPane;
    private TextField currentSpeed = new TextField("0");
    private TextField currentDirection = new TextField("0");
    private Label directionLabel = new Label("");

    public GUI_bottom(){
        bottomPane = bottomPane();
    }

    public SplitPane getBottomPane(){
        return bottomPane;
    }

    private SplitPane bottomPane(){
        SplitPane sp = new SplitPane();
        sp.getItems().addAll(leftBottom());

        return sp;
    }

    public void setSpeed(double speed){
        currentSpeed.setText(String.format("%.1f km/h",speed));
    }

    public void setDircetion(double direction){
        currentDirection.setText(String.format("%.1f°",direction));
        setDirectionLabel(direction);
    }

    private void setDirectionLabel(double direction){
        if(direction >= 0){
            directionLabel.setText("RIGHT");
        } else {
            directionLabel.setText("LEFT");
        }
    }

    private HBox leftBottom(){
        HBox hBox = new HBox(10);

        Label label1 = new Label("Speed:");
        Label label2 = new Label("Turn");

        hBox.getChildren().addAll(label1, currentSpeed, label2, currentDirection);

        return hBox;
    }

}
