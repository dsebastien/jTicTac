package net.dsebastien.jtictac;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.dsebastien.jtictac.config.Configuration;
import net.dsebastien.jtictac.model.TimeSheet;
import net.dsebastien.jtictac.model.TimeSheetEntry;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Entry point.
 */
public class JTicTac extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTicTac.class);
    
    public static void main(String[] args) {
        TimeSheet timeSheet = Configuration.getInstance().loadTimeSheet();
        Configuration.getInstance().saveTimeSheet(timeSheet);
        //launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 320, 240, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setIconified(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("jTicTac 1.0");

        primaryStage.show();


    }
}
