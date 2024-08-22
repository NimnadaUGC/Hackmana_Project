package org.example.hakmana.view.scene;

import com.google.api.client.googleapis.json.GoogleJsonError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.hakmana.model.otherDevices.OtherDevices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.example.hakmana.view.component.DeviceCategoryCardController;
import org.example.hakmana.view.component.PathFinderController;
import org.example.hakmana.view.dialogBoxes.AddNewDevCatController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class OtherDevicesController implements Initializable {
    private static OtherDevicesController instance = null;
    public OtherDevices otherDevicesDb;
    private static javafx.scene.control.ScrollPane dashboardBodyScrollpaneD;
    private PathFinderController dashboardPathFinderControllerD;
    @FXML
    public Button addNew;
    @FXML
    public Button ViewMore;

    @FXML
    public TableView<OtherDevices> otherDeviceTblView;
    @FXML
    public TableColumn<OtherDevices, Integer> num;
    @FXML
    public TableColumn<OtherDevices, String> deviceNameClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> totalClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> activeClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> inactiveClmn;
    @FXML
    public TableColumn<OtherDevices, Integer> repairClmn;
    private String devName;


    private OtherDevicesController() {
    }

    public static OtherDevicesController getInstance() {
        if (instance == null) {
            instance = new OtherDevicesController();
            return instance;
        }
        return instance;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewMore.setDisable(true);

        otherDevicesDb = OtherDevices.getOtherDevicesInstance();
        num.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("num"));
        deviceNameClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, String>("dev"));
        activeClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("numActiveDev"));
        inactiveClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("numInactiveDev"));
        repairClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("numRepairingDev"));
        totalClmn.setCellValueFactory(new PropertyValueFactory<OtherDevices, Integer>("totalDev"));

        addTblRow();
        addTableSelectionListener();
    }

    public void addTblRow() {
        new Thread(() -> otherDeviceTblView.setItems(otherDevicesDb.getObservableOtherDevices())).start();
    }

    public PathFinderController getDashboardPathFinderControllerD() {
        return dashboardPathFinderControllerD;
    }
    public void setDashboardPathFinderControllerD(PathFinderController dashboardPathFinderControllerD) {
        this.dashboardPathFinderControllerD = dashboardPathFinderControllerD;
    }

    public String getDevName() {
        return devName;
    }

    private void addTableSelectionListener() {
        otherDeviceTblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OtherDevices>() {
            @Override
            public void changed(ObservableValue<? extends OtherDevices> observable, OtherDevices oldValue, OtherDevices newValue) {
                if (newValue != null) {
                    devName = newValue.getDev();
                    ViewMore.setDisable(false);
                }
            }
        });
    }

    public void ViewMoreButtonOnAction(ActionEvent actionEvent) {
        DeviceCategoryCardController deviceCategoryCardController=new DeviceCategoryCardController();
        deviceCategoryCardController.setDevName(devName);
        deviceCategoryCardController.setDashboardPathFinderControllerD(getDashboardPathFinderControllerD());
        deviceCategoryCardController.callDeviceInfo();

    }

    public void addNewButtonOnAction(ActionEvent actionEvent) throws IOException {
        AddNewDevCatController addNewDevCatController = AddNewDevCatController.getInstance();
        FXMLLoader addNewDevCatFxmlLoad = new FXMLLoader();
        addNewDevCatFxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.AddNewDevCatController.class.getResource("AddNewDevCat.fxml"));
        addNewDevCatFxmlLoad.setController(addNewDevCatController);
        DialogPane addNewDevCatDialogPane = addNewDevCatFxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addNewDevCatDialogPane);
        dialog.setTitle("Add New Device Category");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.CANCEL) {
            dialog.close();
        }
        tableViewRefresh();
    }

    public void tableViewRefresh(){
        otherDevicesDb.setTblRowLoaded(false);
        OtherDevices.setDevicesLoaded(false);
        otherDevicesDb.getObservableOtherDevices();
        otherDeviceTblView.refresh();
    }

}
