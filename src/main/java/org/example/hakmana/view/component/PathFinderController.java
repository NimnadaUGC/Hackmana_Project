package org.example.hakmana.view.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.view.scene.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PathFinderController extends VBox implements Initializable {
    private static final Logger otherErrorLogger= (Logger) LogManager.getLogger(PathFinderController.class);
    private Stack<String> sceneStack = new Stack<>(); // Create the scene stack
    private String currentScene;//hold the current scene
    private NavPanelController navPanelControllerPath;//reference for navpanel
    private DeviceCategoryCardController deviceCategoryCardController;//reference for device categorycard
    private DeviceInfoCardController deviceInfoCardController;
    private boolean searchBarVisible;
    @FXML
    public HBox searchBar;
    @FXML
    private Label pathTxt;

    @FXML
    public TextField searchTxtField;
    @FXML
    public RadioButton deviceIdRadio;

    private String searchText;
    private Boolean isDevIdSelected;
    private static PathFinderController instance = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DeviceMngmntSmmryScene deviceMngmntSmmryScene=DeviceMngmntSmmryScene.getInstance();

        // Initialize listeners for searchTxtField and userRadio
        searchTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue; // Update searchText dynamically
            deviceMngmntSmmryScene.setSearchText(searchText);
        });

        deviceIdRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            isDevIdSelected = newValue; // Update isUserSelected dynamically
            deviceMngmntSmmryScene.setDevIdSelected(isDevIdSelected);
        });
    }
    public static PathFinderController getInstance() {
        if (instance == null) {
            instance = new PathFinderController();
            return instance;
        }
        return instance;
    }
    public PathFinderController() {
        super();
        FXMLLoader fxmlPathLoader = new FXMLLoader(org.example.hakmana.view.component.PathFinderController.class.getResource("PathFinder.fxml"));
        fxmlPathLoader.setController(this);
        fxmlPathLoader.setRoot(this);
        try{
            fxmlPathLoader.load();
        }
        catch(IOException e){
            otherErrorLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public HBox getSearchBar() {
        return searchBar;
    }

    public DeviceCategoryCardController getDeviceCategoryCardController() {
        return deviceCategoryCardController;
    }
    public void setDeviceCategoryCardController(DeviceCategoryCardController deviceCategoryCardController) {
        this.deviceCategoryCardController = deviceCategoryCardController;
    }
    public void setSearchBarVisible(boolean searchBarVisible) {
        this.searchBarVisible = searchBarVisible;
        searchBar.setVisible(this.searchBarVisible);
    }
    // Getter for searchText
    public String getSearchText() {
        return searchText;
    }

    // Getter for isUserSelected
    public Boolean isUserSelected() {
        return isDevIdSelected;
    }
    public NavPanelController getNavPanelControllerPath() {
        return navPanelControllerPath;
    }
    public void setNavPanelControllerPath(NavPanelController navPanelControllerPath) {
        this.navPanelControllerPath = navPanelControllerPath;
    }
    public Label getPathTxt() {
        return pathTxt;
    }
    public void setPathTxt(String pathTxt) {
        this.pathTxt.setText(pathTxt);
    }
    public DeviceInfoCardController getDeviceInfoCardController() {
        return deviceInfoCardController;
    }
    public void setDeviceInfoCardController(DeviceInfoCardController deviceInfoCardController) {
        this.deviceInfoCardController = deviceInfoCardController;
    }
    //this method is called by the relevant scene controller when scene is change
    public void setBckBtnScene(String bckBtnScene) {
        sceneStack.push(bckBtnScene);
        currentScene=bckBtnScene;
    }
    public void popSceneStack(){
        sceneStack.pop();
    }
    public void printSceneStack(){
        System.out.println(Arrays.toString(sceneStack.toArray()));
    }
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        setPathTxt("");
        checknullContrller(false);
        if (!sceneStack.isEmpty()) {
            String listScenename = sceneStack.pop();

            //To remove current scene from the list.
            //Because current scene is also added to the list
            if (Objects.equals(listScenename, currentScene)) {
                if (!sceneStack.isEmpty()) {
                    listScenename = sceneStack.pop();
                }
            }

            switch (listScenename) {
                case "ReportHndling" -> getNavPanelControllerPath().reportHndlingScene();
                case "DeviceMngmnt" -> getNavPanelControllerPath().deviceMnagmnt();
                case "Overview" -> getNavPanelControllerPath().overviewScene();
                case "UserMngmnt" -> getNavPanelControllerPath().userMngmntScene();
                case "dashboard" -> getNavPanelControllerPath().dashboardScene(event);
                case "DevDetailedView" -> getDeviceInfoCardController().DetailedViewSceneLoad(event);
                case "OtherDevices" -> getDeviceCategoryCardController().loadOtherDevice();
                default -> getDeviceCategoryCardController().loadSmmryScene();
            }

            sceneStack.push(listScenename);
            currentScene = listScenename;
            checknullContrller(true);
        } else {
            System.out.println("list is empty");
        }
    }
    private void checknullContrller(boolean setBoolean){
        if (getDeviceCategoryCardController() != null)
            getNavPanelControllerPath().setCalledFromNavPanel(setBoolean);
        if (getDeviceCategoryCardController() != null)
            getDeviceCategoryCardController().setCalledFromCategoryCard(setBoolean);
    }
}
