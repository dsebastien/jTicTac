package net.dsebastien.jtictac.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import net.dsebastien.jtictac.model.TimeSheetEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sebastien Dubois -- dSebastien
 */
public class JTicTacMainController implements Initializable {
    Logger LOGGER = LoggerFactory.getLogger(JTicTacMainController.class);
    @FXML
    private TableView<TimeSheetEntry> tblTimeSheet;
    @FXML
    private TableView tableView;

    public JTicTacMainController(){
    }

    public void setTblTimeSheet(final TableView<TimeSheetEntry> tblTimeSheet){
        this.tblTimeSheet = tblTimeSheet;
    }

    @FXML
    private void handleButtonClearAction(final ActionEvent event) {
        LOGGER.info("Button Clear clicked!");
    }

    @FXML
    private void handleButtonAddAction(final ActionEvent event) {
        LOGGER.info("Button Add clicked!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}