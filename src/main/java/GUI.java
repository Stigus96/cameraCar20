import javafx.scene.Cursor;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class GUI {
    private Scene scene;

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
        pane.setMinWidth(600);

        return pane;
    }

    private VBox rightBox(){
        VBox vBox = new VBox();
        Button startBtn = new Button("START");
        Button stopBtn = new Button("STOP");
        ToggleButton sensorsOnOffBtn = new ToggleButton("Sensors on/off");

        vBox.getChildren().add(startBtn);
        vBox.getChildren().add(stopBtn);
        vBox.getChildren().add(sensorsOnOffBtn);

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