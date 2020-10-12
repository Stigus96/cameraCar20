import javafx.scene.control.*;

public class GUI_bottom extends SplitPane {

    private SplitPane bottomPane;

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
