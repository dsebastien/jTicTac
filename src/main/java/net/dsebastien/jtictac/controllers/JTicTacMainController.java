package net.dsebastien.jtictac.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import net.dsebastien.jtictac.config.Configuration;
import net.dsebastien.jtictac.model.TimeSheet;
import net.dsebastien.jtictac.model.TimeSheetEntry;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Sebastien Dubois -- dSebastien
 */
public class JTicTacMainController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTicTacMainController.class);

    private final PeriodFormatter hoursMinutesPeriodFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix("h", "h")
            .appendSeparator(", ")
            .appendMinutes()
            .appendSuffix("m", "m")
            .appendSeparator(", ")
            .appendSeconds()
            .appendSuffix("s", "s")
            .toFormatter();

    private final DateTimeFormatter dateTimeFormatter = Configuration.getInstance().getDateTimeFormatter();

    @FXML
    private TableView<TimeSheetEntry> tblTimeSheet;
    @FXML
    private TableColumn<TimeSheetEntry,String> tColTask;
    @FXML
    private TableColumn<TimeSheetEntry,String> tColDuration;
    @FXML
    private TableColumn<TimeSheetEntry,String> tColDate;

    @FXML
    private TextField txtTask;

    @FXML
    private Slider sliderDuration;

    @FXML
    private ToggleButton toggleWholeDay;

    @FXML
    private TitledPane titledPaneTimeSheet;

    public JTicTacMainController(){
    }

    /**
     * Removes all elements
     * @param event
     */
    @FXML
    private void handleButtonRemoveAllItemsAction(final ActionEvent event) {
        final TimeSheet timeSheet = Configuration.getInstance().loadTimeSheet();
        timeSheet.clear();

        saveTimeSheetAndUpdateView(timeSheet);
    }

    /**
     * Removes the selected elements.
     * @param event
     */
    @FXML
    private void handleButtonRemoveSelectedItemsAction(final ActionEvent event) {
        final TimeSheet timeSheet = Configuration.getInstance().loadTimeSheet();

        Set<TimeSheetEntry> selectedItems = new HashSet<TimeSheetEntry>(tblTimeSheet.getSelectionModel().getSelectedItems());
        for(TimeSheetEntry selectedItem: selectedItems){
            timeSheet.getEntries().remove(selectedItem);
        }
        saveTimeSheetAndUpdateView(timeSheet);
    }

    /**
     * Save the changes in the {@link TimeSheet} to disk and updates the view.
     * @param timeSheet
     * @return
     */
    private void saveTimeSheetAndUpdateView(final TimeSheet timeSheet){
        boolean saveSuccessful = Configuration.getInstance().saveTimeSheet(timeSheet);
        if(saveSuccessful){
            //todo replace with Dialog.show...
            Configuration.getInstance().getTrayIcon().displayMessage(null, Configuration.getInstance().getResourceBundle().getString("messages.save.successful"), TrayIcon.MessageType.INFO);
        }else{
            //todo replace with Dialog.show...
            Configuration.getInstance().getTrayIcon().displayMessage(null, Configuration.getInstance().getResourceBundle().getString("messages.save.failed"), TrayIcon.MessageType.ERROR);
        }

        // update the table view
        tblTimeSheet.setItems(FXCollections.observableArrayList(timeSheet.getEntries()));
    }

    /**
     * Creates the new entry; adds it to the current in-memory representation; updates the table view and saves the changes to disk.
     * @param event
     */
    @FXML
    private void handleButtonSaveAction(final ActionEvent event) {
        final TimeSheetEntry timeSheetEntry = new TimeSheetEntry();
        timeSheetEntry.setTask(txtTask.getText());
        timeSheetEntry.setDate(DateTime.now());
        
        long duration;

        if(toggleWholeDay.isSelected()){
            // todo take value from config file (same for fxml slider values)
            duration = 480 * 60 * 1000;
        }else{
            duration = (long) sliderDuration.getValue() * 60 * 1000;
        }

        timeSheetEntry.setDuration(new Duration(duration));

        final TimeSheet timeSheet = Configuration.getInstance().loadTimeSheet();
        timeSheet.addEntry(timeSheetEntry);

        saveTimeSheetAndUpdateView(timeSheet);

        // reset the form
        resetAddEntryForm();

        // show the table
        titledPaneTimeSheet.setExpanded(true);
    }

    private void resetAddEntryForm(){
        txtTask.setText("");
        sliderDuration.setValue(0);
        sliderDuration.setDisable(false);
        toggleWholeDay.setSelected(false);
    }

    /**
     * Reset the add entry form
     * @param event
     */
    @FXML
    private void handleButtonResetAction(final ActionEvent event) {
        resetAddEntryForm();
    }

    @FXML
    private void handleToggleWholeDayAction(final ActionEvent event) {
        if(toggleWholeDay.isSelected()){
            sliderDuration.setDisable(true);
        }else{
            sliderDuration.setDisable(false);
        }
    }

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        //tblTimeSheet.selectionModelProperty().set(TableView.TableViewSelectionModel);
        tblTimeSheet.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // alternative if adding the columns programmatically
        //TableColumn<TimeSheetEntry,String> tColTask = new TableColumn("Task");
        //TableColumn<TimeSheetEntry,String> tColDuration = new TableColumn("Duration");
        //TableColumn<TimeSheetEntry,String> tColDate = new TableColumn("Date");

        tColTask.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TimeSheetEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TimeSheetEntry, String> timeSheetEntryStringCellDataFeatures) {
                final SimpleStringProperty retVal = new SimpleStringProperty();
                retVal.set(timeSheetEntryStringCellDataFeatures.getValue().getTask());
                return retVal;
            }
        });

        tColDuration.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TimeSheetEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TimeSheetEntry, String> timeSheetEntryDurationCellDataFeatures) {
                final SimpleStringProperty retVal = new SimpleStringProperty();
                retVal.set(hoursMinutesPeriodFormatter.print(timeSheetEntryDurationCellDataFeatures.getValue().getDuration().toPeriod()));
                return retVal;
            }
        });
        
        tColDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TimeSheetEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TimeSheetEntry, String> timeSheetEntryStringCellDataFeatures) {
                final SimpleStringProperty retVal = new SimpleStringProperty();
                retVal.set(timeSheetEntryStringCellDataFeatures.getValue().getDate().toString(dateTimeFormatter));
                return retVal;
            }
        });

        // alternative if the data model is observable (with Java FX classes such as SimpleStringProperty, SimpleObjectProperty, ...)
        //tColTask.setCellValueFactory(new PropertyValueFactory<TimeSheetEntry,String>("TaskProperty"));
        //tColDate.setCellValueFactory(new PropertyValueFactory<TimeSheetEntry, DateTime>("DateProperty"));
        //tColDuration.setCellValueFactory(new PropertyValueFactory<TimeSheetEntry, Duration>("DurationProperty"));

        // alternative if adding the columns programmatically
        //tblTimeSheet.getColumns().addAll(tColTask, tColDuration, tColDate);

        tblTimeSheet.setEditable(false);

        // set the data
        TimeSheet timeSheet = Configuration.getInstance().loadTimeSheet();
        tblTimeSheet.setItems(FXCollections.observableArrayList(timeSheet.getEntries()));
    }
}
