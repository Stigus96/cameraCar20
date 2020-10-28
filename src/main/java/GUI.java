import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GUI {
    private Scene scene;

    String HOST_NAME = "158.38.143.180";
    int PORT = 1300;

    public GUI(){
        setMainScene();
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
        return new VBox(topBox(),bottomPane());
    }

    private HBox topBox(){
        HBox hBox = new HBox(leftPane(), rightBox());
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

    private VBox rightBox(){
        VBox vBox = new VBox();
        Button startBtn = new Button("START");
        Button stopBtn = new Button("STOP");
        ToggleButton sensorsOnOffBtn = new ToggleButton("Sensors on/off");

        startBtn.setMaxWidth(Double.MAX_VALUE);
        stopBtn.setMaxWidth(Double.MAX_VALUE);
        sensorsOnOffBtn.setMaxWidth(Double.MAX_VALUE);

        vBox.setSpacing(15);
        vBox.setPadding(new Insets(30,20,30,20));
        vBox.getChildren().addAll(startBtn,stopBtn,sensorsOnOffBtn);
        return vBox;
    }

    private SplitPane bottomPane(){
        SplitPane sp = new SplitPane();
        sp.getItems().add(leftBottom());
        sp.getItems().add(rightBottom());

        return sp;
    }

    private TabPane leftBottom(){
        TabPane tp = new TabPane();

        Tab tab1 = new Tab("Guide", new Label("Shows what you can do with the robot"));
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