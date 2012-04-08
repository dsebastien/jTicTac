package net.dsebastien.jtictac;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Entry point.
 */
public class JTicTac extends Application {
    public static void main(String[] args) {
        launch(args);
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
