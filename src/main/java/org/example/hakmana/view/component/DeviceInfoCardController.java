package org.example.hakmana.view.component;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.view.scene.DevDetailedViewController;
import org.example.hakmana.view.dialogBoxes.AddNoteDialogPane;
import org.example.hakmana.model.noteHndling.NoteTable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeviceInfoCardController extends AnchorPane implements Initializable {
    private static final Logger otherErrorLogger= (Logger) LogManager.getLogger(DeviceInfoCardController.class);
     //For get the main dashboard body scroll pane
     private static javafx.scene.control.ScrollPane dashboardBodyScrollpaneDD;
     private static PathFinderController dashboardPathFinderControllerDD;

     @FXML
     private Button DetailedViewBtn;
     @FXML
     private TextField devIdTxt;
     @FXML
     private TextField brandTxt;
     @FXML
     private TextField userTxt;
     @FXML
     private VBox noteTxtArea;
     @FXML
     private AnchorPane root;
     @FXML
     private Pane addBtn;
     @FXML
     private Pane moreInfoBtn;
     @FXML
     private String devId;
     private   ArrayList<String> paneControllers=new ArrayList<String>();
     private ArrayList<String> username=new ArrayList<String>();
     private String deviceCat;
     private String user;
     private String brand;
     private String note;
     private Button editButton;
     private Button updateButton;
     private ArrayList<String> id=new ArrayList<String>();

     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
     }
     public DeviceInfoCardController() {
          super();
          FXMLLoader fxmlFooterLoader = new FXMLLoader(DeviceInfoCardController.class.getResource("DeviceInfoCard.fxml"));
          fxmlFooterLoader.setController(this);
          fxmlFooterLoader.setRoot(this);

          try {
               fxmlFooterLoader.load();
          }
          catch (IOException e){
              otherErrorLogger.error(e.getMessage());
               throw new RuntimeException(e);
          }

     }

    public String getDevId() {
        return devId;
    }
    public void setDeviceCat(String deviceCat) {
        this.deviceCat = deviceCat;
    }
    public String getDeviceCat() {
        return deviceCat;
    }
    public String getUser() {
        return user;
    }
    public String getBrand() {
        return brand;
    }
    public String getNote() {
        return note;
    }
    public static ScrollPane getDashboardBodyScrollpaneDD() {
        return dashboardBodyScrollpaneDD;
    }
    public static void setDashboardBodyScrollpaneDD(ScrollPane dashboardBodyScrollpaneDD) {
        DeviceInfoCardController.dashboardBodyScrollpaneDD = dashboardBodyScrollpaneDD;
    }
    public static PathFinderController getDashboardPathFinderControllerDD() {
        return dashboardPathFinderControllerDD;
    }

    public static void setDashboardPathFinderControllerDD(PathFinderController dashboardPathFinderControllerDD) {
        DeviceInfoCardController.dashboardPathFinderControllerDD = dashboardPathFinderControllerDD;
    }

    @FXML
     private void onMouseEntered() {
          root.setScaleX(1.1);
          root.setScaleY(1.1);
     }

     @FXML
     private void onMouseExited() {
          root.setScaleX(1.0);
          root.setScaleY(1.0);
     }

    public void setDevId(String devId) {
        //creating database connection
        this.devId = devId;
        devIdTxt.setText(this.devId);
        //add notes to the deviceInfoCard
        setNoteForCard(devId);
        id.add(devId);
    }
    public void setNoteForCard(String devids){
        int index=0;
        NoteTable instance=NoteTable.getInstance();
        ArrayList list;
        list=instance.setNoteForCard(devids);
        while(index< list.size()) {
            Button btn= (Button) list.get(index);
            btn.setOnAction(event->{
                NoteTable noteInstance=NoteTable.getInstance();
                String titles=btn.getText();
                String[] data=new String[5];
                data = noteInstance.viewQueries(titles,devids);
                FXMLLoader fxmlLoader = new FXMLLoader(org.example.hakmana.view.dialogBoxes.AddNoteDialogPane.class.getResource("AddnoteDialog.fxml"));
                AddNoteDialogPane dialogPane=AddNoteDialogPane.getInstance();
                fxmlLoader.setController(dialogPane);
                try {

                    DialogPane dialog1 = fxmlLoader.load();
                } catch (IOException e) {
                    otherErrorLogger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                AddNoteDialogPane dialogpane = fxmlLoader.getController();
                dialogpane.getEditButton().setVisible(false);
                editButton = dialogpane.getEditButton();
                dialogpane.getAddNote().setVisible(false);
                dialogpane.getUpdateButton().setVisible(false);
                updateButton = dialogpane.getUpdateButton();
                dialogpane.setIds(devids);
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
                Optional<ButtonType> check= dialog.showAndWait();
                if(check.isPresent() && check.get()==ButtonType.CLOSE) {
                    dialog.close();
                }

            });
            btn.setStyle("-fx-pref-width: 190px;-fx-alignment: CENTER_LEFT;-fx-background-color: white");
            btn.setOnMouseEntered(e->{
                btn.setStyle("-fx-background-color: lightblue;-fx-pref-width: 190px;-fx-alignment: CENTER_LEFT;");
            });
            btn.setOnMouseExited(e->{
                btn.setStyle("-fx-background-color: white;-fx-pref-width: 190px;-fx-alignment: CENTER_LEFT;");
            });
            noteTxtArea.getChildren().add((Node) list.get(index));
            index++;
        }

    }

    public void setUser(String user) {
          this.user = user;
          userTxt.setText(this.user);
          username.add(user);
    }

    public void setBrand(String brand) {
          this.brand = brand;
          brandTxt.setText(this.brand);
     }


    //handle more info button to load DevDetailedView scene
     @FXML
     public void DetailedViewSceneLoad(ActionEvent event) throws IOException {
         getDashboardPathFinderControllerDD().setDeviceInfoCardController(this);
         getDashboardPathFinderControllerDD().setSearchBarVisible(false);
         // Load the FXML loader for the target scene
          FXMLLoader detailDeviceVboxLoder = new FXMLLoader(DevDetailedViewController.class.getResource("DevDetailedView.fxml"));
          //create DevDetailedViewController instance
          DevDetailedViewController devDetailedViewController=DevDetailedViewController.getInstance();
          detailDeviceVboxLoder.setController(devDetailedViewController);
          getDashboardPathFinderControllerDD().setPathTxt("Device Management>"+getDeviceCat()+">"+getDevId());
          getDashboardPathFinderControllerDD().setBckBtnScene("DevDetailedView");
          devDetailedViewController.setOtherDevCat(getDeviceCat());
          try{
             VBox vbox=detailDeviceVboxLoder.load();
             getDashboardBodyScrollpaneDD().setContent(vbox);//this scollpane id knows only that controller file

          } catch (IOException e) {
              otherErrorLogger.error(e.getMessage());
              throw new RuntimeException(e);
          }
          //setters
          devDetailedViewController.setDeviceSelector(getDeviceCat());
          devDetailedViewController.setDevRegNum(getDevId());
          devDetailedViewController.setDashboardPathFinderControllerD(getDashboardPathFinderControllerDD());
          devDetailedViewController.showDeviceDetail();


     }

     //note adding dialog box
     public void popupdialog() {
          FXMLLoader noteFxmlLoader = new FXMLLoader();
          noteFxmlLoader.setLocation(org.example.hakmana.view.dialogBoxes.AddNoteDialogPane.class.getResource("AddnoteDialog.fxml"));

          try {

              AddNoteDialogPane dialogpane1=AddNoteDialogPane.getInstance();
              noteFxmlLoader.setController(dialogpane1);
               DialogPane dialogPane = noteFxmlLoader.load();

              dialogpane1=noteFxmlLoader.getController();
              dialogpane1.getEditButton().setVisible(false);
              dialogpane1.getUpdateButton().setVisible(false);
              dialogpane1.setSetDeviceIdName(id.get(0));
              dialogpane1.setUser(username.get(0));
              Dialog<ButtonType> dialog = new Dialog<>();
              dialog.setDialogPane(dialogpane1.getDialogpane1());
              dialog.setTitle("ADD NOTE");
              Optional<ButtonType> check = dialog.showAndWait();
              if(check.isPresent() && check.get()==ButtonType.CLOSE){
                  noteTxtArea.getChildren().clear();
                  setDevId(devId);
                  dialog.close();
              }
          } catch (IOException e) {
              otherErrorLogger.error(e.getMessage());
               throw new RuntimeException(e);
          }
     }

}
