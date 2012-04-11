package net.dsebastien.jtictac.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sebastien Dubois -- dSebastien
 */
public class JTicTacAddEntryController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTicTacAddEntryController.class);

    @FXML
    private TextField txtTask;

    @FXML
    private Slider sliderDuration;

    @FXML
    private ToggleButton toggleWholeDay;

    @FXML
    private ToggleButton toggleToday;

    @FXML
    private void handleButtonSaveAction(final ActionEvent event) {
        LOGGER.info("Button Save clicked!");
    }

    @FXML
    private void handleButtonResetAction(final ActionEvent event) {
        LOGGER.info("Button Reset clicked!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}