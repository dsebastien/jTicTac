package net.dsebastien.jtictac;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.dsebastien.jtictac.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ResourceBundle;


/**
 * Entry point.
 *
 * @author Sebastien Dubois -- dSebastien
 */
public class JTicTac extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTicTac.class);
    private boolean isVisible = false;

    /**
     * Entry point.
     * @param args
     */
    public static void main(String[] args) {
        LOGGER.info("Initialization...");
        Application.launch(JTicTac.class, args);
    }

    /**
     * Invoked automatically by JavaFX (<code>Application.launch(...)</code>)
     */
    public JTicTac() {

    }

    @Override
    public void start(final Stage stage) throws Exception {
        LOGGER.info("Checking if the application is already running");
        boolean firstInstance = lockInstance(".lock");

        if (!firstInstance) {
            LOGGER.error("Another instance is already running");
            ResourceBundle resourceBundle = Configuration.getInstance().getResourceBundle();
            name.antonsmirnov.javafx.dialog.Dialog.showError(resourceBundle.getString("messages.errors.alreadystarted.title"), resourceBundle.getString("messages.errors.alreadystarted"));
        } else {
            // initialize the timesheet
            Configuration.getInstance().loadTimeSheet();

            //stage.setResizable(false);
            stage.setTitle(Configuration.getInstance().getApplicationName() + " v" + Configuration.getInstance().getApplicationVersion());

            stage.setResizable(false);
            // primarystage: stagestyle, position, sizetoscene, requestfocus (on show)

            Parent jTicTacMain = FXMLLoader.load(JTicTac.class.getResource("/JTicTacMain.fxml"), Configuration.getInstance().getResourceBundle());

            Scene scene = new Scene(jTicTacMain);

            // add the custom stylesheet
            String jTicTacCSSPath = JTicTac.class.getResource("/JTicTac.css").toExternalForm();
            scene.getStylesheets().addAll(jTicTacCSSPath);

            // Set the stage
            stage.setScene(scene);
            stage.sizeToScene();

            stage.getIcons().add(new Image(Configuration.getInstance().getApplicationIconPath()));

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
                    LOGGER.error("The System Tray icon could not be added!", e);
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

    /**
     * Ensures that only one instance of the application can run
     * Source: http://stackoverflow.com/questions/177189/how-to-implement-a-single-instance-java-application
     *
     * @param lockFile the file to use as witness to know if the app is already running
     * @return true if this is the first instance of the app; false if another one is already running
     */
    private static boolean lockInstance(final String lockFile) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        try {
                            fileLock.release();
                            randomAccessFile.close();
                            file.delete();
                        } catch (Exception e) {
                            LOGGER.error("Unable to remove lock file: " + lockFile, e);
                        }
                    }
                });
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create and/or lock file: " + lockFile, e);
        }
        return false;
    }
}
