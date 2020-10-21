import javafx.scene.control.*;
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
        sp.getItems().addAll(leftBottom(), rightBottom());

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

    private TabPane leftBottom(){
        TabPane tp = new TabPane();

        Label label1 = new Label("Speed:");
        Label label2 = new Label("Turn");
        VBox speedOmeter = new VBox(label1, currentSpeed, label2, currentDirection, directionLabel);

        Tab tab1 = new Tab("SpeedOmeter", speedOmeter);
        Tab tab2 = new Tab("Rapport"  , new Label("Rapport skriving blir har :)"));
        Tab tab3 = new Tab("Pictures" , new Label("Pictures of the process"));

        tp.getTabs().add(tab1);
        tp.getTabs().add(tab2);
        tp.getTabs().add(tab3);

        return tp;
    }

    private Accordion rightBottom(){
        Accordion acc = new Accordion();

        TitledPane pane1 = new TitledPane("Items" , new Label("Show all items available"));
        TitledPane pane2 = new TitledPane("Functions"  , new Label("Show all functions available"));
        TitledPane pane3 = new TitledPane("Producers", new Label("Siggi, Stig, Erlend"));

        acc.getPanes().add(pane1);
        acc.getPanes().add(pane2);
        acc.getPanes().add(pane3);

        return acc;
    }
}
