package net.dsebastien.jtictac;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.dsebastien.jtictac.config.Configuration;
import net.dsebastien.jtictac.model.TimeSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


/**
 * Entry point.
 * @author Sebastien Dubois -- dSebastien
 */
public class JTicTac extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTicTac.class);
    private boolean isVisible = false;

    public static void main(String[] args) {
        Application.launch(JTicTac.class, args);
    }

    public JTicTac(){
        // initialize the timesheet
        Configuration.getInstance().loadTimeSheet();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle(Configuration.getInstance().getApplicationName()+" v"+Configuration.getInstance().getApplicationVersion());

        // primarystage: stagestyle, position, sizetoscene, requestfocus (on show)


        //Parent root = FXMLLoader.load(JTicTac.class.getResource("/JTicTacAddEntryController.fxml"));
        Parent root = FXMLLoader.load(JTicTac.class.getResource("/JTicTacMain.fxml"));

        //Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show(); // todo remove
        // scene: addmnemonic, getaccelerators


        // Tray icon
        final TrayIcon trayIcon;

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            MouseListener mouseListener = new MouseListener() {

                public void mouseClicked(final MouseEvent e) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            /*
                            if(isVisible){
                                stage.hide();
                            }else{
                                stage.show();
                            }
                            */
                        }
                    });
                }

                public void mouseEntered(final MouseEvent e) {
                }

                public void mouseExited(final MouseEvent e) {
                }

                public void mousePressed(final MouseEvent e) {
                }

                public void mouseReleased(final MouseEvent e) {
                }
            };

            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LOGGER.info("Exiting...");
                    System.exit(0);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(Configuration.getInstance().getSystemTrayIcon(), Configuration.getInstance().getApplicationName(), popup);

            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    trayIcon.displayMessage("Action Event",
                            "An Action Event Has Been Performed!",
                            TrayIcon.MessageType.INFO);
                }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                LOGGER.error("The System Tray icon could not be added!",e);
                System.exit(0);
                // todo be more polite
            }
        } else {
            LOGGER.error("System Tray not supported! Exiting...");
            // todo log additional info (platform etc)
            System.exit(0);
            // todo be more polite
        }
    }
}
