package net.dsebastien.jtictac.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import net.dsebastien.jtictac.config.Configuration;
import net.dsebastien.jtictac.model.TimeSheet;
import net.dsebastien.jtictac.model.TimeSheetEntry;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

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
    private StackPane tableContainer;

    @FXML
    private TextField txtTask;

    @FXML
    private Slider sliderDuration;

    @FXML
    private ToggleButton toggleWholeDay;

    private TimeSheetEntry selectedTimeSheetEntry;


    public JTicTacMainController(){
    }
    
    @FXML
    private void handleButtonClearAction(final ActionEvent event) {
        LOGGER.info("Button Clear clicked!");
        //todo implement
    }

    @FXML
    private void handleButtonSaveAction(final ActionEvent event) {
        LOGGER.info("Button Save clicked!");
        //todo implement
    }

    @FXML
    private void handleButtonResetAction(final ActionEvent event) {
        LOGGER.info("Button Reset clicked!");
        //todo implement
    }

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // alternative if adding the columns programmatically
        //TableColumn<TimeSheetEntry,String> tColTask = new TableColumn("Task");
        //TableColumn<TimeSheetEntry,String> tColDuration = new TableColumn("Duration");
        //TableColumn<TimeSheetEntry,String> tColDate = new TableColumn("Date");

        tColTask.setEditable(false);
        tColDuration.setEditable(false);
        tColDate.setEditable(false);

        tColTask.setPrefWidth(400);
        tColDuration.setPrefWidth(100);
        tColDate.setPrefWidth(80);

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
