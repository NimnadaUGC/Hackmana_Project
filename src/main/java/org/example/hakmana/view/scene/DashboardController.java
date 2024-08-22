package org.example.hakmana.view.scene;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.DashboardCardTableController;
import org.example.hakmana.GetNoteController;
import org.example.hakmana.model.noteHndling.NoteTable;
import org.example.hakmana.model.noteHndling.setTableColumnData;
import org.example.hakmana.view.component.FooterController;
import org.example.hakmana.view.component.HeaderController;
import org.example.hakmana.view.component.NavPanelController;
import org.example.hakmana.view.component.PathFinderController;
import org.example.hakmana.view.dialogBoxes.AddDeviceDialogController;
import org.example.hakmana.view.dialogBoxes.AddDeviceUserDialogController;
import org.example.hakmana.view.dialogBoxes.AddNoteDialogPane;
import org.example.hakmana.view.dialogBoxes.LoadDeviceByRegNumDialogController;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;

public class DashboardController extends Component implements Initializable {
    private static final Logger otherErrorLogger = (Logger) LogManager.getLogger(DashboardController.class);
    private static DashboardController instance = null;
    private static Stage windowStage;
    @FXML
    public HeaderController headerController;//header custom component injector
    @FXML
    public NavPanelController navPanelController;//NavPanel custom component injector
    public AnchorPane parentAnchor;
    @FXML
    private PathFinderController pathFinderControlle;
    public FooterController footerController;
    @FXML
    public PathFinderController pathFinderController;
    @FXML
    public ScrollPane bodyScrollPane;
    @FXML
    private VBox bodyComponet;//injector for VBox to expand
    @FXML
    private Stage stage;
    @FXML
    private VBox vbox1, vbox2, vbox3, vbox4, vbox5;
    @FXML
    private TableView<DashboardCardTableController> table2;
    @FXML
    private TableColumn<DashboardCardTableController, String> activeCol;
    @FXML
    private TableColumn<DashboardCardTableController, String> inActiveCol;
    @FXML
    private TableColumn<DashboardCardTableController, String> repairingCol;
    @FXML
    private TableColumn<DashboardCardTableController, String> notAssignCol;
    @FXML
    private TableColumn<DashboardCardTableController, String> totalCol;

    @FXML
    private TableView<GetNoteController> table1;
    @FXML
    private TableColumn<GetNoteController, String> col1;
    @FXML
    private TableColumn<GetNoteController, String> col2;
    @FXML
    private TableColumn<GetNoteController, Date> col3;
    private String titles;
    private String ids;
    private TextField titl1;
    private TextField user1;
    private TextField id1;
    private TextArea note1;
    private Label date1;
    private Button editButton;
    private Button updateButton;
    private NoteTable noteInstance=NoteTable.getInstance();
    private static final String LOG_FILE_PATH1 = "src/main/resources/logs/systemuser.log"; // Path to the log file
    private static final String LOG_FILE_PATH2 = "src/main/resources/logs/sql_exceptions.log"; // Path to the log file
    private static final String LOG_FILE_PATH3 = "src/main/resources/logs/other_exceptions.log"; // Path to the log file
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int RETENTION_DAYS = 90; // Retention period in days
    ObservableList<DashboardCardTableController> list1;
    private DashboardController() {
    }

    public NavPanelController getNavPanelController() {
        return navPanelController;
    }

    public static DashboardController getInstance() {
        if (instance == null) {
            instance = new DashboardController();
            return instance;
        }
        return instance;
    }

    public void initialize(URL location, ResourceBundle resources) {
        //clean the log files
        cleanLogFile(LOG_FILE_PATH1, RETENTION_DAYS);
        cleanLogFile(LOG_FILE_PATH2, RETENTION_DAYS);
        cleanLogFile(LOG_FILE_PATH3, RETENTION_DAYS);

        //automaticaly upadate the cards
        //create the connections
        int count1;
        int count2;
        int count3;
        int count4;
        //get numbers of columns from database
        int size = noteInstance.getTableNamesQuiries("tablesize");
        String[] table;
        table = noteInstance.getArray(size);
        //update the cards
        for (int j = 0; j < size; j++) {
            count1 = 0;
            count2 = 0;
            count3 = 0;
            count4 = 0;

            if ((table[j].equals("desktop")) || (table[j].equals("photocopymachine")) || (table[j].equals("monitor")) || (table[j].equals("multimediaProjector")) || (table[j].equals("laptop")) || (table[j].equals("ups"))) {

                switch (table[j]) {
                    case "desktop" -> dashboardCardUpdate(count1, count2, count3, count4, table[j], "DesRegNum");
                    case "photocopymachine" ->
                            dashboardCardUpdate(count1, count2, count3, count4, table[j], "PhotoCopyMachineRegNum");
                    case "monitor" -> dashboardCardUpdate(count1, count2, count3, count4, table[j], "MonitorRegNum");
                    case "multimediaProjector" ->
                            dashboardCardUpdate(count1, count2, count3, count4, table[j], "MultimediaProjectorRegNum");
                    case "laptop" -> dashboardCardUpdate(count1, count2, count3, count4, table[j], "LaptopRegNum");
                    case "ups" -> dashboardCardUpdate(count1, count2, count3, count4, table[j], "UpsRegNum");
                }
            }

        }

        headerController.setFontSize("3em");
        headerController.setTitleMsg("Welcome");
        headerController.setUsernameMsg("Mr.Udara Mahanama");
        headerController.setDesignationMsg("Development Officer");

        //set the dashboard scroll pane in navigation panel
        navPanelController.setDashboardBodyScrollpane(bodyScrollPane);
        //set the pathfinder controller in navigation panel

        NavPanelController.setDashboardpathFinderController(pathFinderController);

        //set the navigation panel controller in pathfinder controller
        pathFinderController.setNavPanelControllerPath(navPanelController);
        pathFinderController.setSearchBarVisible(false);
        pathFinderController.setBckBtnScene("dashboard");
//
//        Stage windowStage = (Stage) node.getScene().getWindow();
//        windowStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
//            double oldStageWidth=oldValue.doubleValue();
//            double newStageWidth=newValue.doubleValue();
//            if(oldStageWidth>newStageWidth) {
//                //116
//                System.out.println(getBodyComponet().getWidth());
//                //getBodyComponet().setFillWidth(true);
//                System.out.println("collapse");
//                System.out.println(getBodyComponet().getWidth());
//            }
//            else {
//                System.out.println(getBodyComponet().getWidth());
//                System.out.println("expand");
//                // getBodyComponet().setFillWidth(true);
//                System.out.println(getBodyComponet().getWidth());
//            }
//        }));
        //create the event listener to the navigation panel ToggleButton() method
//        navPanelController.collapseStateProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                expand();
//            } else {
//                collapse();
//            }
//        });
        tableAdd();
        addDataOfDevice();


    }

    public VBox getBodyComponet() {
        return bodyComponet;
    }

    public void setWindowStage(Stage windowStage) {
        DashboardController.windowStage = windowStage;

    }

    public void dashboardCardUpdate(int count1, int count2, int count3, int count4, String tableValue, String regNum) {
        count1 = noteInstance.setPrValues(regNum, tableValue, "Active");
        String statement1 = Integer.toString(count1);

        count3 = noteInstance.setPrValues(regNum, tableValue, "Repairing");
        String statement2 = Integer.toString(count3);

        count2 = noteInstance.setPrValues(regNum, tableValue, "Inactive");
        String statement3 = Integer.toString(count2);

        count4 = noteInstance.setPrValues(regNum, tableValue, "NotAssign");
        String statement4 = Integer.toString(count4);

        noteInstance.getDeviceStatus(statement1, statement3, statement4, statement2, tableValue);
    }

    //add status of device to the table
    public void addDataOfDevice() {
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        list1= noteInstance.getDeviceStatus(null, null, null, null, null);
        activeCol.setCellValueFactory(new PropertyValueFactory<DashboardCardTableController, String>("activeDevices"));
        inActiveCol.setCellValueFactory(new PropertyValueFactory<DashboardCardTableController, String>("inactiveDevices"));
        repairingCol.setCellValueFactory(new PropertyValueFactory<DashboardCardTableController, String>("repairedDevices"));
        notAssignCol.setCellValueFactory(new PropertyValueFactory<DashboardCardTableController, String>("notAssignedDevices"));
        totalCol.setCellValueFactory(new PropertyValueFactory<DashboardCardTableController, String>("totalDevices"));
        table2.setItems(list1);
    }

    public void tableAdd() {
        table1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        setTableColumnData controller = new setTableColumnData();
        ObservableList<GetNoteController> list = controller.getNote();
        col1.setCellValueFactory(new PropertyValueFactory<GetNoteController, String>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<GetNoteController, String>("title"));
        col3.setCellValueFactory(new PropertyValueFactory<GetNoteController, Date>("date"));
        table1.setItems(list);

    }

    /*+++++++++++++++++++++++++++++Note feature++++++++++++++++++++++++++++++++++++++*/
    public void delete() {
        noteInstance = NoteTable.getInstance();
        int selectedValue = table1.getSelectionModel().getSelectedIndex();
        System.out.println(selectedValue);
        if(selectedValue >= 0) {
            Alert.AlertType type = Alert.AlertType.CONFIRMATION;
            Alert alert = new Alert(type, "");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(stage);
            alert.getDialogPane().setContentText("do you want to delete this note?");
            alert.getDialogPane().setHeaderText("confirmation!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String titles = table1.getItems().get(selectedValue).getTitle();
                String ids = table1.getItems().get(selectedValue).getId();
                table1.getItems().remove(selectedValue);
                table1.getSelectionModel().clearSelection();
                noteInstance.deleteTableQueries(ids, titles);
            }
            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                table1.getSelectionModel().clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "please select a note", "alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void view() {
        int selectedValue = table1.getSelectionModel().getSelectedIndex();
        String[] data = new String[5];
        if (selectedValue >= 0) {
            String titles = table1.getItems().get(selectedValue).getTitle();
            String ids = table1.getItems().get(selectedValue).getId();
            data = noteInstance.viewQueries(titles, ids);
            System.out.println("checking2");
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(AddNoteDialogPane.class.getResource("AddnoteDialog.fxml")));
            AddNoteDialogPane dialogpane = AddNoteDialogPane.getInstance();
            fxmlLoader.setController(dialogpane);
            try {
                DialogPane dialog1 = fxmlLoader.load();
                dialogpane = fxmlLoader.getController();
                dialogpane.getEditButton().setVisible(true);
                editButton = dialogpane.getEditButton();
                dialogpane.getAddNote().setVisible(false);
                dialogpane.getUpdateButton().setVisible(false);
                updateButton = dialogpane.getUpdateButton();
                dialogpane.setIds(ids);
                TextField titl1 = dialogpane.getTitle();
                TextArea note1 = dialogpane.getNote();
                TextField user1 = dialogpane.getUsername();
                TextField id1 = dialogpane.getDeviceId();
                Label date1 = dialogpane.getDate();
                titl1.setText(data[4]);
                note1.setText(data[2]);
                user1.setText(data[1]);
                id1.setText(data[0]);
                String date = data[3].toString();
                date1.setText(date);
                titl1.setEditable(false);
                note1.setEditable(false);
                user1.setEditable(false);
                id1.setEditable(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogpane.getDialogpane1());
                dialog.setTitle("ADD NOTE");
                editButton.setOnAction(e -> {
                    id1.setEditable(true);
                    titl1.setEditable(true);
                    user1.setEditable(true);
                    note1.setEditable(true);
                    updateButton.setVisible(true);
                });
                updateButton.setOnAction(e1 -> {
                    LocalDate localDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String currentDate = localDate.format(formatter);
                    try {
                        noteInstance.updateTableQuiries(id1.getText(), user1.getText(), note1.getText(), titl1.getText(), currentDate, titles, ids);
                        tableAdd();
                        JOptionPane.showMessageDialog(this, "update successful!", "successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException event) {
                        JOptionPane.showMessageDialog(this, "same title with same id not valid", "Rejected!", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        table1.getSelectionModel().clearSelection();
                    }
                });
                Optional<ButtonType> check = dialog.showAndWait();
                if (check.isPresent() && check.get() == ButtonType.CLOSE) {

                }
            } catch (IOException e) {
                otherErrorLogger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void Add() {
        FXMLLoader noteFxmlLoad = new FXMLLoader(org.example.hakmana.view.dialogBoxes.AddNoteDialogPane.class.getResource("AddnoteDialog.fxml"));

        AddNoteDialogPane addNoteDialogPane = AddNoteDialogPane.getInstance();

        noteFxmlLoad.setController(addNoteDialogPane);

        try {
            DialogPane notedialogPane = noteFxmlLoad.load();
            addNoteDialogPane = noteFxmlLoad.getController();
            addNoteDialogPane.getEditButton().setVisible(false);
            addNoteDialogPane.getUpdateButton().setVisible(false);
            addNoteDialogPane.getAddNote().setVisible(true);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(notedialogPane);
            dialog.setTitle("ADD NOTE");

            Optional<ButtonType> check = dialog.showAndWait();
            if (check.isPresent() && check.get() == ButtonType.CLOSE) {
                tableAdd();
            }
        } catch (IOException e) {
            otherErrorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /*+++++++++++++++++++++++++++++Animations++++++++++++++++++++++++++++++++++++++*/
//    private void Animation(double animStartPos, double animEndPos) {
//        //Animation object refernce
//        TranslateTransition bodyExpand = new TranslateTransition(Duration.millis(300), bodyComponet);
//        bodyExpand.setFromX(animStartPos);
//        bodyExpand.setToX(animEndPos); // expand VBox
//        bodyExpand.setAutoReverse(true);
//        bodyExpand.play();
//
//    }
//
//    public void expand() {
//        double W1 = bodyComponet.getWidth() + 244;
//        Animation(0, -244);
//        bodyComponet.setFillWidth(true);
//        //bodyComponet.setMinWidth(W1);
//    }
//
//    public void collapse() {
//        double W1 = bodyComponet.getWidth() - 244;
//        Animation(-244, 0);
//        bodyComponet.setFillWidth(true);
//        //bodyComponet.setMinWidth(W1);
//    }

    /*+++++++++++++++++++++++++++++Device adding dialog pane++++++++++++++++++++++++++++++++++++++*/
    public void addDeviceBtnDialogOpen(ActionEvent event) throws IOException {
        FXMLLoader addDevicefxmlLoad = new FXMLLoader();
        addDevicefxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.AddDeviceDialogController.class.getResource("AddDeviceDialog.fxml"));

        AddDeviceDialogController addDeviceDialogController = AddDeviceDialogController.getInstance();
        addDevicefxmlLoad.setController(addDeviceDialogController);

        DialogPane addDeviceDialogPane = addDevicefxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addDeviceDialogPane);
        dialog.setTitle("Add Device");

        Optional<ButtonType> clickedButton = dialog.showAndWait();

    }

    /*+++++++++++++++++++++++++++++Device User add dialog pane++++++++++++++++++++++++++++++++++++++*/
    public void addDeviceUser(ActionEvent event) throws IOException {
        FXMLLoader addDeviceUserDialogFxmlLoad = new FXMLLoader();
        addDeviceUserDialogFxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.AddDeviceUserDialogController.class.getResource("AddDeviceUserDialog.fxml"));

        AddDeviceUserDialogController addDeviceUserDialogController=AddDeviceUserDialogController.getInstance();
        addDeviceUserDialogFxmlLoad.setController(addDeviceUserDialogController);

        DialogPane addDeviceDialogPane = addDeviceUserDialogFxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addDeviceDialogPane);
        dialog.setTitle("Add Device User");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
    }

    private void commonTaskInScene(LoadDeviceByRegNumDialogController loadDeviceByRegNumDialogController){
        LoadDeviceByRegNumDialogController.setDashboardPathFinderController(pathFinderController);
        LoadDeviceByRegNumDialogController.setDashboardBodyScrollpane(bodyScrollPane);
        loadDeviceByRegNumDialogController.setCatSelected(false);
        loadDeviceByRegNumDialogController.setRegNumSelected(false);
    }
    private int loadQuickScene(LoadDeviceByRegNumDialogController loadDeviceByRegNumDialogController, Dialog<ButtonType> dialog){
        if(!(loadDeviceByRegNumDialogController.isCatSelected() && loadDeviceByRegNumDialogController.isRegNumSelected())){
            dialog.showAndWait();
            return 0;
        }
        loadDeviceByRegNumDialogController.loadDevDetailedscene();
        return 1;
    }
    private void dialogButtons(LoadDeviceByRegNumDialogController loadDeviceByRegNumDialogController, Dialog<ButtonType> dialog){
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK) {
            loadQuickScene(loadDeviceByRegNumDialogController,dialog);
        }
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.CANCEL) {
            dialog.close();
        }
    }
    /*+++++++++++++++++++++++++++++See other device dialog pane++++++++++++++++++++++++++++++++++++++*/
    public void otherDeviceQuickAccess(ActionEvent event) throws IOException {
        FXMLLoader loadDeviceByRegNumDiallogFxmlLoad = new FXMLLoader();
        loadDeviceByRegNumDiallogFxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.AddDeviceUserDialogController.class.getResource("LoadDeviceByRegNumDialog.fxml"));

        LoadDeviceByRegNumDialogController loadDeviceByRegNumDialogController=LoadDeviceByRegNumDialogController.getInstance();
        loadDeviceByRegNumDiallogFxmlLoad.setController(loadDeviceByRegNumDialogController);
        loadDeviceByRegNumDialogController.setFromOtherDevice(true);
        commonTaskInScene(loadDeviceByRegNumDialogController);

        DialogPane addDeviceDialogPane = loadDeviceByRegNumDiallogFxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addDeviceDialogPane);
        dialog.setTitle("Other device details");

        dialogButtons(loadDeviceByRegNumDialogController,dialog);

    }
    /*+++++++++++++++++++++++++++++Show main device++++++++++++++++++++++++++++++++++++++*/
    public void mainDeviceQuickAccess(ActionEvent event) throws IOException {
        FXMLLoader loadDeviceByRegNumDiallogFxmlLoad = new FXMLLoader();
        loadDeviceByRegNumDiallogFxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.AddDeviceUserDialogController.class.getResource("LoadDeviceByRegNumDialog.fxml"));

        LoadDeviceByRegNumDialogController loadDeviceByRegNumDialogController=LoadDeviceByRegNumDialogController.getInstance();
        loadDeviceByRegNumDiallogFxmlLoad.setController(loadDeviceByRegNumDialogController);
        loadDeviceByRegNumDialogController.setFromOtherDevice(false);
        commonTaskInScene(loadDeviceByRegNumDialogController);

        DialogPane addDeviceDialogPane = loadDeviceByRegNumDiallogFxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addDeviceDialogPane);
        dialog.setTitle("Main device details");

        dialogButtons(loadDeviceByRegNumDialogController,dialog);

    }
    //log cleaner function
    public static void cleanLogFile(String filePath, int retentionDays) {
        File logFile = new File(filePath);
        List<String> filteredLines = new ArrayList<>();

        LocalDateTime thresholdDate = LocalDateTime.now().minus(retentionDays, ChronoUnit.DAYS);

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the date is at the beginning of the log entry in the format yyyy-MM-dd HH:mm:ss
                if (!line.trim().isEmpty()) {
                    String dateString = line.substring(0,21);
                    String Dates[]=dateString.split(",",2);
                    String curretntDate=Dates[0]+Dates[1].trim();
                    LocalDateTime logDate = LocalDateTime.parse(curretntDate, DATE_TIME_FORMATTER);

                    if (!logDate.isBefore(thresholdDate)) {
                        filteredLines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
            for (String logLine : filteredLines) {
                writer.write(logLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



