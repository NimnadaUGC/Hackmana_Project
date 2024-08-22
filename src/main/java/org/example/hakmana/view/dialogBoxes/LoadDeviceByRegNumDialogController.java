package org.example.hakmana.view.dialogBoxes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.DatabaseConnection;
import org.example.hakmana.model.otherDevices.OtherDevices;
import org.example.hakmana.view.component.DeviceInfoCardController;
import org.example.hakmana.view.component.NavPanelController;
import org.example.hakmana.view.component.PathFinderController;
import org.example.hakmana.view.scene.DevDetailedViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoadDeviceByRegNumDialogController implements Initializable {
    private static LoadDeviceByRegNumDialogController instance=null;
    private final DatabaseConnection databaseConnection=DatabaseConnection.getInstance();
    private final OtherDevices otherDevices=OtherDevices.getOtherDevicesInstance();
    private static final Logger otherErrorLogger= (Logger) LogManager.getLogger(DeviceInfoCardController.class);

    //For get the main dashboard body scroll pane
    private static javafx.scene.control.ScrollPane dashboardBodyScrollpane;
    private static PathFinderController dashboardPathFinderController;
    @FXML//device category selector
    public ChoiceBox<String> devCat;
    @FXML//device Registration number selector
    public ChoiceBox<String> devRegNum;

    private String selectedDevice;
    private String selectedDevRegNum;

    private final String[] mainDevCategories={"Desktop","Photocopy Machine","Monitor","Projectors","Laptop","Printer","UPS"};
    private List<String> otherDevCategories;
    private ArrayList<String> devRegNumbers;;
    private boolean fromOtherDevice;
    private boolean catSelected;
    private boolean regNumSelected;

    private LoadDeviceByRegNumDialogController() {
    }

    public static LoadDeviceByRegNumDialogController getInstance() {
        if(instance==null){
            instance=new LoadDeviceByRegNumDialogController();
            return instance;
        }
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateDevCatChoiceBox();
        devCat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setSelectedDevice(newValue);
            populateDevRegChoiceBoxes();
        });
        devRegNum.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           setSelectedDevRegNum(newValue);
        });

    }

    public static ScrollPane getDashboardBodyScrollpane() {
        return dashboardBodyScrollpane;
    }

    public static void setDashboardBodyScrollpane(ScrollPane dashboardBodyScrollpane) {
        LoadDeviceByRegNumDialogController.dashboardBodyScrollpane = dashboardBodyScrollpane;
    }

    public static PathFinderController getDashboardPathFinderController() {
        return dashboardPathFinderController;
    }

    public static void setDashboardPathFinderController(PathFinderController dashboardPathFinderController) {
        LoadDeviceByRegNumDialogController.dashboardPathFinderController = dashboardPathFinderController;
    }

    public String getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(String selectedDevice) {
        this.selectedDevice = selectedDevice;
        setCatSelected(true);
    }

    public String getSelectedDevRegNum() {
        return selectedDevRegNum;
    }

    public void setSelectedDevRegNum(String selectedDevRegNum) {
        this.selectedDevRegNum = selectedDevRegNum;
        setRegNumSelected(true);
    }

    public List<String> getOtherDevCategories() {
        return otherDevCategories;
    }

    public void setOtherDevCategories(List<String> otherDevCategories) {
        this.otherDevCategories = otherDevCategories;
    }

    public ArrayList<String> getDevRegNumbers() {
        return devRegNumbers;
    }

    public void setDevRegNumbers(ArrayList<String> devRegNumbers) {
        this.devRegNumbers = devRegNumbers;
    }

    public boolean isFromOtherDevice() {
        return fromOtherDevice;
    }

    public void setFromOtherDevice(boolean fromOtherDevice) {
        this.fromOtherDevice = fromOtherDevice;
    }

    public boolean isCatSelected() {
        return catSelected;
    }

    public void setCatSelected(boolean catSelected) {
        this.catSelected = catSelected;
    }

    public boolean isRegNumSelected() {
        return regNumSelected;
    }

    public void setRegNumSelected(boolean regNumSelected) {
        this.regNumSelected = regNumSelected;
    }

    //according to the button selected fetch table name from other device table
    private void fetchOtherDevices(){
        setOtherDevCategories(otherDevices.loadForQuickAccess());
    }
    private void populateDevCatChoiceBox(){
        if(isFromOtherDevice()){
            fetchOtherDevices();
            devCat.getItems().addAll(getOtherDevCategories());
        }else{
            devCat.getItems().addAll(mainDevCategories);
        }
    }

    //When device cat selected the reg numbers from tables
    private void fetchDevReg(){
        if(devRegNumbers!=null){
            devRegNum.getItems().removeAll(getDevRegNumbers());
        }
        setDevRegNumbers(databaseConnection.quickAccessLoadDevRegnum(getSelectedDevice()));
    }

    private void populateDevRegChoiceBoxes(){
        fetchDevReg();
        devRegNum.getItems().addAll(getDevRegNumbers());
    }

    public void loadDevDetailedscene(){
            getDashboardPathFinderController().setSearchBarVisible(false);
            String selDevForScene="";
            // Load the FXML loader for the target scene
            FXMLLoader detailDeviceVboxLoder = new FXMLLoader(DevDetailedViewController.class.getResource("DevDetailedView.fxml"));
            //create DevDetailedViewController instance
            DevDetailedViewController devDetailedViewController=DevDetailedViewController.getInstance();
            detailDeviceVboxLoder.setController(devDetailedViewController);
            switch (getSelectedDevice()) {
                case "Desktop" -> selDevForScene="Desktop";
                case "Photocopy Machine" -> selDevForScene="Photocopy Machines";
                case "Monitor" -> selDevForScene="Monitors";
                case "Projectors" -> selDevForScene="Projectors";
                case "Laptop" -> selDevForScene="Laptops";
                case "Printer" -> selDevForScene="Printers";
                case "UPS" -> selDevForScene="UPS";
                default -> selDevForScene=getSelectedDevice();
            }
            getDashboardPathFinderController().setPathTxt("Device Management>"+selDevForScene+">"+getSelectedDevRegNum());
            getDashboardPathFinderController().setBckBtnScene("DevDetailedView");
            devDetailedViewController.setOtherDevCat(selDevForScene);
            try{
                VBox vbox=detailDeviceVboxLoder.load();
                getDashboardBodyScrollpane().setContent(vbox);//this scollpane id knows only that controller file

            } catch (IOException e) {
                otherErrorLogger.error(e.getMessage());
                throw new RuntimeException(e);
            }
            //setters
            devDetailedViewController.setLoadFromQuick(true);
            devDetailedViewController.setDeviceSelector(selDevForScene);
            devDetailedViewController.setDevRegNum(getSelectedDevRegNum());
            devDetailedViewController.showDeviceDetail();


    }

}
