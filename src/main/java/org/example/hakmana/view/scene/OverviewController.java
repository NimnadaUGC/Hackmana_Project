package org.example.hakmana.view.scene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.GetNoteController;
import org.example.hakmana.model.mainDevices.*;
import org.example.hakmana.model.overviewTable.LogEntry;
import org.example.hakmana.model.overviewTable.OverviewTableData;
import org.example.hakmana.model.userMngmnt.DeviceUser;
import org.example.hakmana.model.userMngmnt.SystemUser;
import org.example.hakmana.view.component.AddDevButtonController;

import javax.management.monitor.Monitor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class OverviewController implements Initializable {
    @FXML
    TableView<LogEntry> historyTable;
    @FXML
    TableColumn<LogEntry,String> dateColumn;
    @FXML
    TableColumn<LogEntry,String> processColumn;
    @FXML
    TableColumn<LogEntry,String> detailsColumn;
    @FXML
    private ChoiceBox <String> users;
    @FXML
    private  ChoiceBox<String> devIds;
    @FXML
    private ChoiceBox<String> subChoiceBox;
    @FXML
    private Label label1;
    private static OverviewController instance=null;
    private ArrayList<String> allUsers=new ArrayList<>();
    private OverviewTableData controller=new OverviewTableData();
    private OverviewController(){}

    public static OverviewController getInstance() {
        if(instance== null){
            instance=new OverviewController();
            return instance;
        }


        return instance;
    }

    public void setValueForSubChoiceBox(ChoiceBox<String> subChoiceBox,String mainSelection){
        ObservableList<String> subOptions;
        label1.setVisible(true);
        if (mainSelection.equals("Desktop")) {
            subOptions = FXCollections.observableArrayList(Desktop.getDesktopInstance().getAllDesktops());
        }
        else if (mainSelection.equals("Laptop")) {
            subOptions = FXCollections.observableArrayList(Laptops.getLaptopsInstance().getAllLaptops());

        }
        else if (mainSelection.equals("PhotocopyMatchines")) {
            subOptions = FXCollections.observableArrayList(PhotocpyMchine.getPhotocpyMchineInstance().getAllPhotoCopyMachine());
        }
        else if (mainSelection.equals("Monitor")) {
            subOptions = FXCollections.observableArrayList(Monitors.getMonitorInstance().getAllMonitors());

        }
        else if (mainSelection.equals("UPS")) {
            subOptions = FXCollections.observableArrayList(UPS.getUpsInstance().getAllUps());

        }
        else if (mainSelection.equals("Projector")) {
            subOptions = FXCollections.observableArrayList(Projectors.getProjectorsInstance().getAllProjectors());

        }
        else if (mainSelection.equals("printers")) {
            subOptions = FXCollections.observableArrayList(Printer.getPrinterInstance().getAllPrinters());
        }
        else{
            subOptions = FXCollections.observableArrayList("all");
        }
        subChoiceBox.setItems(subOptions);
        subChoiceBox.setValue("all");
        if (!subOptions.isEmpty()) {
            subChoiceBox.setValue(subOptions.get(0));  // Set default value for sub ChoiceBox

        }

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get SystemUsers
        label1.setVisible(false);
        SystemUser devInstance=new SystemUser();
        allUsers=devInstance.getAllSystemUsers();
        if(allUsers!=null){
            users.getItems().addAll(allUsers);
            users.setValue("all");
        }
        //set devIds
        ObservableList<String> mainOptions = FXCollections.observableArrayList("Desktop","Laptop","PhotocopyMatchines","Monitor","UPS","Projector","printers","all");
        devIds.setItems(mainOptions);
        devIds.setValue("all");

        devIds.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setValueForSubChoiceBox(subChoiceBox, newValue);
            }
        });
        subChoiceBox.setValue("all");
        devIds.setOnAction(this::filter);
        users.setOnAction(this::filter);
        subChoiceBox.setOnAction(this::filter);
        //add data to the table

        ObservableList<LogEntry> list= controller.setColumnData(users,devIds,subChoiceBox);
        dateColumn.setCellValueFactory(new PropertyValueFactory<LogEntry,String>("timestamp"));
        processColumn.setCellValueFactory(new PropertyValueFactory<LogEntry,String>("process"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("details"));
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        historyTable.setItems(list);

    }

    private void filter(ActionEvent actionEvent) {

        ObservableList<LogEntry> list= controller.setColumnData(users,devIds,subChoiceBox);
        dateColumn.setCellValueFactory(new PropertyValueFactory<LogEntry,String>("timestamp"));
        processColumn.setCellValueFactory(new PropertyValueFactory<LogEntry,String>("process"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("details"));
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        historyTable.setItems(list);
    }



}
