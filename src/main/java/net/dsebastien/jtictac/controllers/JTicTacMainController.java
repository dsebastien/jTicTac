package net.dsebastien.jtictac.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import net.dsebastien.jtictac.config.Configuration;
import net.dsebastien.jtictac.model.TimeSheet;
import net.dsebastien.jtictac.model.TimeSheetEntry;
import org.javafxdata.control.TableViewFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sebastien Dubois -- dSebastien
 */
public class JTicTacMainController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTicTacMainController.class);

    private final PeriodFormatter hoursMinutesPeriodFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix(" hour", " hours")
            .appendSeparator(", ")
            .appendMinutes()
            .appendSuffix(" minute", " minutes")
            .appendSeparator(", ")
            .appendSeconds()
            .appendSuffix(" second", " seconds")
            .toFormatter();

    private final DateTimeFormatter dateTimeFormatter = Configuration.getInstance().getDateTimeFormatter();

    @FXML
    private TableView<TimeSheetEntry> tblTimeSheet;

    @FXML
    private StackPane stackPane;

    private TimeSheetEntry selectedTimeSheetEntry;


    public JTicTacMainController(){
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
        //S - The type of the TableView generic type (i.e. S == TableView<S>)
        //T - The type of the content in all cells in this TableColumn.

        tblTimeSheet = new TableView<TimeSheetEntry>();

        // todo replace by resource bundle usage

        // init columns + cell factory
        TableColumn<TimeSheetEntry,String> tColTask = new TableColumn("Task");
        TableColumn<TimeSheetEntry,String> tColDuration = new TableColumn("Duration");
        TableColumn<TimeSheetEntry,String> tColDate = new TableColumn("Date");

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

        tblTimeSheet.getColumns().addAll(tColTask, tColDuration, tColDate);

        // bind data
        TimeSheet timeSheet = Configuration.getInstance().loadTimeSheet();
        tblTimeSheet.setItems(FXCollections.observableArrayList(timeSheet.getEntries()));




        /*
        tblTimeSheet = TableViewFactory.
                create(TimeSheetEntry.class, timeSheet.getEntries()).selectColumns("Task", "Duration", "Date").buildTableView();
        
        for(TableColumn column: tblTimeSheet.getColumns()){
            System.out.println(column);
        }
        */




        stackPane.getChildren().add(tblTimeSheet);
    }
}
