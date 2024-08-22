package org.example.hakmana.view.scene;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.mainDevices.*;
import org.example.hakmana.model.otherDevices.OtherDevices;
import org.example.hakmana.model.userMngmnt.DeviceUser;
import org.example.hakmana.view.component.DeviceCategoryCardController;
import org.example.hakmana.view.component.DeviceInfoCardController;
import org.example.hakmana.view.component.NavPanelController;
import org.example.hakmana.view.component.PathFinderController;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DevDetailedViewController implements Initializable {
    private static final Logger otherErrorLogger= (Logger) LogManager.getLogger(DevDetailedViewController.class);
    private static DevDetailedViewController instance=null;
    private DeviceInfoCardController deviceInfoCardController;
    private OtherDevicesController otherDevicesController=OtherDevicesController.getInstance();
    private PathFinderController dashboardPathFinderControllerD=PathFinderController.getInstance();
    private String devName;
    private boolean loadFromQuick;


    //Device details common
    @FXML
    public ChoiceBox<String> StatusChoiceBox;
    @FXML
    public TextField modelTextField;
    @FXML
    public TextField regNumTextField;

    //other details
    private ArrayList<HBox> otherHboxList;
    private ArrayList<Label> otherLblList;
    private ArrayList<TextField> otherTextList;
    private ArrayList<ChoiceBox<String>> otherChoiceBoxList;
    private ArrayList<HBox> otherChoiceBoxHboxList;
    @FXML
    public VBox otherDetailVbox;
    @FXML
    public Label other1Lbl;
    @FXML
    public HBox other1Hbox;
    @FXML
    public TextField other1;
    @FXML
    public HBox other2Hbox;
    @FXML
    public Label other2Lbl;
    @FXML
    public TextField other2;
    @FXML
    public HBox other3Hbox;
    @FXML
    public Label other3Lbl;
    @FXML
    public TextField other3;
    @FXML
    public HBox other4Hbox;
    @FXML
    public Label other4Lbl;
    @FXML
    public TextField other4;
    @FXML
    public TextField other5;
    @FXML
    public Label other5Lbl;
    @FXML
    public TextField other6;
    @FXML
    public Label other6Lbl;
    @FXML
    public HBox other5Hbox;
    public ChoiceBox<String> hardSizeChoiceBox;
    @FXML
    public HBox other6Hbox;
    @FXML
    public ChoiceBox<String> ramSizeChoiceBox;


    //Operating system
    @FXML
    public HBox other7Hbox;
    @FXML
    public ChoiceBox<String> OSChoiseBox;

    //Other Choice box
    @FXML
    public VBox otherChoiceBoxVbox;
    @FXML
    public HBox other8Hbox;
    @FXML
    public ChoiceBox<String> FloppyDiskChoiceBox;
    @FXML
    public HBox other9Hbox;
    @FXML
    public ChoiceBox<String> SoundCardChoiceBox;
    @FXML
    public HBox other10Hbox;
    @FXML
    public ChoiceBox<String> TVCardChoiceBox;
    @FXML
    public HBox other11Hbox;
    @FXML
    public ChoiceBox<String> NetworkCardChoiceBox;
    @FXML
    public HBox other12Hbox;
    @FXML
    public ChoiceBox<String> SsdChoiceBox;
    @FXML
    public HBox other13Hbox;
    @FXML
    public ChoiceBox<String> CdRomChoiceBox;
    @FXML
    public HBox other14Hbox;
    @FXML
    public ChoiceBox<String> UpsChoiceBox;
    @FXML
    public HBox other15Hbox;
    @FXML
    public ChoiceBox<String> PowerSupplyChoiceBox;


    //input Dev details
    private ArrayList<HBox> inputHboxList;
    private ArrayList<Label> inputLblList;
    private ArrayList<ChoiceBox<String>> inputChoiceBoxList;
    @FXML
    public VBox inputVbox;
    @FXML
    public HBox input1Hbox;
    @FXML
    public Label input1Lbl;
    @FXML
    public ChoiceBox<String> inputChoiceBox1;
    @FXML
    public HBox input2Hbox;
    @FXML
    public Label input2Lbl;
    @FXML
    public ChoiceBox<String> inputChoiceBox2;
    @FXML
    public HBox input3Hbox;
    @FXML
    public Label input3Lbl;
    @FXML
    public ChoiceBox<String> inputChoiceBox3;
    @FXML
    public HBox input4Hbox;
    @FXML
    public Label input4Lbl;
    @FXML
    public ChoiceBox<String> inputChoiceBox4;
    

    //output Dev details
    private ArrayList<HBox> outputHboxList;
    private ArrayList<Label> outputLblList;
    private ArrayList<ChoiceBox<String>> outputChoiceBoxList;
    @FXML
    public VBox outputVbox;
    @FXML
    public HBox output1Hbox;
    @FXML
    public Label output1Lbl;
    @FXML
    public ChoiceBox<String> outputChoiceBox1;
    @FXML
    public HBox output2Hbox;
    @FXML
    public Label output2Lbl;
    @FXML
    public ChoiceBox<String> outputChoiceBox2;
    @FXML
    public HBox output3Hbox;
    @FXML
    public Label output3Lbl;
    @FXML
    public ChoiceBox<String> outputChoiceBox3;


    //deviceUser details
    private ArrayList<TextField> userTextLsit;
    @FXML
    public VBox userDetailsVbox;
    @FXML
    public Button assignUserBtn;
    @FXML
    public TextField userNIC;
    private String initialUser;//this variable use to identify initial deviceUser to compare
    @FXML
    public TextField userTitle;
    @FXML
    public TextField userName;
    @FXML
    public TextField userGmail;

    @FXML
    public HBox interactionHbox;
    @FXML
    public Button editBtn;
    @FXML
    public Button saveBtn;
    @FXML
    public  Button resetBtn;
    @FXML
    public Button deleteBtn;

    ArrayList<String> newValues=new ArrayList<>();
    private final String[] deviceStatus={"Active","Repairing","Inactive","Not Assigned"};
    private final String[] YN={"Yes","No"};
    private final String[] WinLin={"Windows","Linux"};
    private final String[] OnboardDecicated={"On Board","Dedicated","No"};
    private final String[] sizeTypeList={"MB","GB","TB"};
    private String otherDevCat;

    private static String deviceSelector;
    private static String devRegNum;
    private  TranslateTransition bodyExpand;//Animation object refernce
    LoginPageController newInstance=LoginPageController.getInstance();
    private DevDetailedViewController(){}
    public static DevDetailedViewController getInstance() {
        if(instance==null){
            instance=new DevDetailedViewController();
            return instance;
        }
        return instance;
    }

    public void initialize(URL location, ResourceBundle resources) {
        populateChoiceBx();

        StatusChoiceBox.getItems().addAll(deviceStatus);
        OSChoiseBox.getItems().addAll(WinLin);

        OSChoiseBox.setDisable(false);
        hardSizeChoiceBox.setDisable(false);
        ramSizeChoiceBox.setDisable(false);

        //get all the other details vbox label and Hboxes
        otherHboxList=new ArrayList<>(List.of(other1Hbox,other2Hbox,other3Hbox,other4Hbox,other5Hbox,other6Hbox,
                other7Hbox));
        otherLblList=new ArrayList<>(List.of(other1Lbl,other2Lbl,other3Lbl,other4Lbl));
        otherTextList=new ArrayList<>(List.of(other1,other2,other3,other4));
        otherChoiceBoxHboxList=new ArrayList<>(List.of(other8Hbox,other9Hbox,other10Hbox,other11Hbox,
                other12Hbox,other13Hbox,other14Hbox,other15Hbox));
        otherChoiceBoxList=new ArrayList<>(List.of(FloppyDiskChoiceBox,
                SoundCardChoiceBox,TVCardChoiceBox,NetworkCardChoiceBox,
                SsdChoiceBox,CdRomChoiceBox,UpsChoiceBox,PowerSupplyChoiceBox));

        //get all the input vbox label and Hboxes and textfield
        inputHboxList=new ArrayList<>(List.of(input1Hbox,input2Hbox,input3Hbox,input4Hbox));
        inputLblList=new ArrayList<>(List.of(input1Lbl,input2Lbl,input3Lbl,input4Lbl));
        inputChoiceBoxList=new ArrayList<>(List.of(inputChoiceBox1,inputChoiceBox2,inputChoiceBox3,inputChoiceBox4));


        //get all the output vbox label and Hboxes and textfield
        outputHboxList=new ArrayList<>(List.of(output1Hbox,output2Hbox,output3Hbox));
        outputLblList=new ArrayList<>(List.of(output1Lbl,output2Lbl,output3Lbl));
        outputChoiceBoxList=new ArrayList<>(List.of(outputChoiceBox1,outputChoiceBox2,outputChoiceBox3));


        //get all the deviceUser textfield
        userTextLsit=new ArrayList<>(List.of(userNIC,userName,userTitle,userGmail));

        //set the all vboxes not visible and not editable at the begining
        reset();

        //Realtime get the Users
        userNIC.textProperty().addListener((observable, oldValue, newValue) -> {
            // Enable the submitButton only if regNumTextField is not empty
            assignUserBtn.setDisable(newValue.isEmpty());

            // Check if the newValue is available in the users array
            DeviceUser deviceUser =DeviceUser.getDeviceUserInstance().isNicAvailable(newValue);
            if (deviceUser != null) {
                // Auto-fill the other text fields
                userGmail.setText(deviceUser.getGmail());
                userName.setText(deviceUser.getName());
                userTitle.setText(deviceUser.getTitle());
            }
        });

        // Disable the submitButton initially if regNumTextField is empty
        assignUserBtn.setDisable(userNIC.getText().isEmpty());

    }

    /*-------------------------Getter and Setter--------------------------------*/
    public PathFinderController getDashboardPathFinderControllerD() {
        return dashboardPathFinderControllerD;
    }
    public void setDashboardPathFinderControllerD(PathFinderController dashboardPathFinderControllerD) {
        this.dashboardPathFinderControllerD = dashboardPathFinderControllerD;
    }
    public String getDevRegNum() {
        return devRegNum;
    }
    public void setDevRegNum(String devRegNum) {
        DevDetailedViewController.devRegNum = devRegNum;
    }
    public void setDeviceSelector(String deviceSelector) {
        DevDetailedViewController.deviceSelector = deviceSelector;
    }
    public String getOtherDevCat() {
        return otherDevCat;
    }
    public void setOtherDevCat(String otherDevCat) {
        this.otherDevCat = otherDevCat;
    }
    public boolean isLoadFromQuick() {
        return loadFromQuick;
    }
    public void setLoadFromQuick(boolean loadFromQuick) {
        this.loadFromQuick = loadFromQuick;
    }

    //populate choice boxes (input choice boxes,output choice boxes,ups,power supply)
    private void setChoBox(ArrayList<String> items,ChoiceBox<String> choBox) {
        if(choBox.getItems().isEmpty()){
            choBox.getItems().addAll(items);
        }
        choBox.getItems().add("No");
    }
    //populate the choice boxes(floppy disk,cdrom,network card,sound card,tv card,ssd,cd)
    private void populateChoiceBx(){
        FloppyDiskChoiceBox.getItems().addAll(YN);
        CdRomChoiceBox.getItems().addAll(YN);

        NetworkCardChoiceBox.getItems().addAll(OnboardDecicated);
        SoundCardChoiceBox.getItems().addAll(OnboardDecicated);
        TVCardChoiceBox.getItems().addAll(OnboardDecicated);

        SsdChoiceBox.getItems().addAll(YN);
    }
    //choice box visibility
    private void ChoboxVisiblity(VBox vbox,ArrayList<HBox> hbxList, ArrayList<ChoiceBox<String>> choiceboxList,boolean isVisible){
        vbox.setVisible(isVisible);
        for(HBox outputHbox:hbxList){
            outputHbox.setVisible(isVisible);
        }
        for(ChoiceBox<String> outputChoiceBox:choiceboxList){
            outputChoiceBox.setVisible(isVisible);
        }

    }
    //set the All Choice box Disability
    private void setChoiceBoxDisability(boolean isDisable, ArrayList<ChoiceBox<String>> choiceBoxList){
        for(ChoiceBox<String> choBox:choiceBoxList){
            choBox.setDisable(isDisable);
        }
    }
    private void acceptOnlyNumbers(TextField textField){
        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void showDeviceDetail(){
        switch (deviceSelector) {
            case "Desktop" -> {
                Desktop desktop = Desktop.getDesktopInstance().getDevice(getDevRegNum());
                setCommonToView(desktop);
                setOtherDetails(new String[]{"Purchased Form","Serial Number","Processor"},
                        desktop.getPurchasedFrom(),desktop.getSerialNum(),desktop.getProcessor());
                userDetails(desktop.getUserNIC());
                other5Hbox.setVisible(true);//hard disk
                other5Lbl.setText("Hard Disk");
                if(desktop.getHardDisk()==null){
                    other5.setText("No");
                }
                else{
                    other5.setText(desktop.getHardDisk());
                }
                other6Hbox.setVisible(true);//ram
                other6Lbl.setText("RAM");
                if(desktop.getHardDisk()==null){
                    other6.setText("No");
                }
                else{
                    other6.setText(desktop.getRam());
                }
                other7Hbox.setVisible(true);//os
                OSChoiseBox.setValue(desktop.getOs());
                acceptOnlyNumbers(other5);
                acceptOnlyNumbers(other6);

                //populate the choice boxes
                hardSizeChoiceBox.getItems().addAll(sizeTypeList);
                ramSizeChoiceBox.getItems().addAll(sizeTypeList);
                setChoBox(desktop.getUPSRegNums(),UpsChoiceBox);
                setChoBox(desktop.getPowerSuppliesRegNum(),PowerSupplyChoiceBox);
                setChoBox(desktop.getMousesRegNum(),inputChoiceBox1);
                setChoBox(desktop.getKeyboardsRegNum(),inputChoiceBox2);
                setChoBox(desktop.getMicsRegNum(),inputChoiceBox3);
                setChoBox(desktop.getScannersRegNum(),inputChoiceBox4);
                setChoBox(desktop.getMonitorsRegNum(),outputChoiceBox1);
                setChoBox(desktop.getSpeakersRegNum(),outputChoiceBox2);
                setChoBox(desktop.getPrintersRegNum(),outputChoiceBox3);

                setOtherChoBoxValues(desktop.getFloppyDisk(),desktop.getSoundCard(),desktop.getTvCard(),
                        desktop.getNetworkCard(),desktop.getSsd(),desktop.getCdRom(),desktop.getUpsRegNum(),desktop.getPowerSupplyRegNum());
                setOutputDetails(new String[]{"Monitor Register Number","Speaker Register Number","Printer Registration number"},desktop.getMonitorRegNum(),desktop.getSpeakerRegNum(),desktop.getPrinterRegNum());
                setInputDetails(new String[]{"Mouse Register Number","Keyboard Register Number","Mic Register Number","Scanner Register Number"},desktop.getMouseRegNum(),desktop.getKeyboardRegNum(),desktop.getMicRegNum(),desktop.getScannerRegNum());
            }
            case "Photocopy Machines" ->{
                PhotocpyMchine photocpyMchine=PhotocpyMchine.getPhotocpyMchineInstance().getDevice(getDevRegNum());
                setCommonToView(photocpyMchine);
                setOtherDetails(new String[]{"Purchased From"},photocpyMchine.getPurchasedFrom());
            }
            case "Monitors" ->{
                Monitors monitor=Monitors.getMonitorInstance().getDevice(getDevRegNum());
                setCommonToView(monitor);
                setOtherDetails(new String[]{"Screen Size","Purchased From"},monitor.getScreenSize(),monitor.getPurchasedFrom());
            }
            case "Projectors" -> {
                Projectors projector=Projectors.getProjectorsInstance().getDevice(getDevRegNum());
                setCommonToView(projector);
                setOtherDetails(new String[]{"Purchased From"},projector.getPurchasedFrom());
            }
            case "Laptops" -> {
                    Laptops laptop = Laptops.getLaptopsInstance().getDevice(getDevRegNum());
                    setCommonToView(laptop);
                    setOtherDetails(new String[]{"Purchased Form","Processor"}, laptop.getPurchasedFrom(),laptop.getCpu());
                    userDetails(laptop.getUserNIC());

                    other5Hbox.setVisible(true);//hard disk
                    other5Lbl.setText("Storage");
                    if(laptop.getStorage()==null){
                        other5.setText("No");
                    }
                    else{
                        other5.setText(laptop.getStorage());
                    }

                    other6Hbox.setVisible(true);//ram
                    other6Lbl.setText("RAM");
                    if(laptop.getStorage()==null){
                        other6.setText("No");
                    }
                    else{
                        other6.setText(laptop.getRam());
                    }

                    other7Hbox.setVisible(true);//os
                    OSChoiseBox.setValue(laptop.getOs());
                    acceptOnlyNumbers(other5);
                    acceptOnlyNumbers(other6);

                    setChoBox(laptop.getMousesRegNum(),inputChoiceBox1);
                    setChoBox(laptop.getKeyboardsRegNum(),inputChoiceBox2);
                    setInputDetails(new String[]{"Mouse Registration number","Keyboard Registration number"},laptop.getMouseRegNum(), laptop.getKeyboardRegNum());
                }
            case "Printers" -> {
                Printer printer =Printer.getPrinterInstance().getDevice(getDevRegNum());
                setCommonToView(printer);
                setOtherDetails(new String[]{"Serial Number","Paper Input","Paper Output","Purchased From"},
                        printer.getSerialNum(),printer.getPaperInput(),printer.getPaperOutput(),printer.getPurchasedFrom());
            }
            case "UPS" -> {
                    UPS ups =UPS.getUpsInstance().getDevice(getDevRegNum());
                    setCommonToView(ups);
                    setOtherDetails(new String[]{"Purchased From"},ups.getPurchasedFrom());
            }
            default -> {
                OtherDevices otherDevices=OtherDevices.getOtherDevicesInstance().getOtherevice(getDevRegNum(),getOtherDevCat());
                setCommonToView(otherDevices);
                setOtherDetails(new String[]{"Purchased From"},otherDevices.getPurchasedFrom());
            }
        }

    }

    /*---------------------Create Configurations--------------------*/
    private void setEditable(ArrayList<TextField> textFieldslist,boolean setEdit,String color){
        for(TextField textField:textFieldslist){
            textField.setEditable(setEdit);
            textField.setStyle("-fx-border-color: "+color+";-fx-border-width: 2;-fx-border-radius: 5");
        }
    }
    private void getTextFieldText(ArrayList<TextField> textFieldslists){
        for(TextField textField:textFieldslists){
            if(textField.getText().isEmpty()){
                continue;
            }
            newValues.add(textField.getText());
        }
    }
    private void getChoiceBoxValue(ArrayList<ChoiceBox<String>> choiceBoxList){
        for(ChoiceBox<String> choBox:choiceBoxList){
            if(Objects.equals(choBox.getValue(), "")){
                break;
            }
            if(choBox.isVisible()){
                if(Objects.equals(choBox.getValue(), "No")){
                    newValues.add(null);
                    continue;
                }
                newValues.add((String) choBox.getValue());
            }

        }
    }

    /*---------------------Set the values in TextField--------------------------*/
    private void setCommonToView(Devices devCommon){
        regNumTextField.setText(devCommon.getRegNum());
        modelTextField.setText(devCommon.getModel());
        StatusChoiceBox.setValue(devCommon.getStatus());
    }
    private void setOtherDetails(String[] otherlblText,String ...setOtherTextField){
        otherDetailVbox.setVisible(true);
        for(int i=0;i< setOtherTextField.length;i++){
            otherHboxList.get(i).setVisible(true);
            if(setOtherTextField[i]==null){
                otherTextList.get(i).setText("No");
            }
            else{
                otherTextList.get(i).setText(setOtherTextField[i]);
            }
            otherLblList.get(i).setText(otherlblText[i]);
        }
    }
    private void setInputDetails(String[] inputLblText,String ...choBoxVal){
        inputVbox.setVisible(true);
        for(int i=0;i< inputLblText.length;i++){
            inputHboxList.get(i).setVisible(true);
            if(choBoxVal[i]==null){
                inputChoiceBoxList.get(i).setValue("No");
            }
            else{
                inputChoiceBoxList.get(i).setValue((choBoxVal[i]));
            }
            inputLblList.get(i).setText(inputLblText[i]);
        }
    }
    private void setOutputDetails(String[] outputLblText,String ...choBoxVal){
        outputVbox.setVisible(true);
        for(int i=0;i< outputLblText.length;i++){
            outputHboxList.get(i).setVisible(true);
            if(choBoxVal[i]==null){
                outputChoiceBoxList.get(i).setValue("No");
            }
            else{
                outputChoiceBoxList.get(i).setValue(choBoxVal[i]);
            }
            outputLblList.get(i).setText(outputLblText[i]);
        }

    }
    private void setOtherChoBoxValues(String ...choBoxVal){
        otherChoiceBoxVbox.setVisible(true);
        for(int i=0;i< choBoxVal.length;i++){
            otherChoiceBoxHboxList.get(i).setVisible(true);
            otherChoiceBoxList.get(i).setVisible(true);
            if(choBoxVal[i]==null){
                otherChoiceBoxList.get(i).setValue("No");
            }
            else{
                otherChoiceBoxList.get(i).setValue(choBoxVal[i]);
            }
        }
    }


    /*------------------------General Interactions-----------------------------*/
    @FXML
    private void edit(){
        setEditable(new ArrayList<>(List.of(modelTextField,other5,other6)),true,"#03AED2");
        setEditable(otherTextList,true,"#03AED2");
        saveBtn.setDisable(false);
        resetBtn.setDisable(false);
        setChoiceBoxDisability(false,new ArrayList<>(List.of(StatusChoiceBox,hardSizeChoiceBox,ramSizeChoiceBox,OSChoiseBox)));
        setChoiceBoxDisability(false,inputChoiceBoxList);
        setChoiceBoxDisability(false,outputChoiceBoxList);
        setChoiceBoxDisability(false,otherChoiceBoxList);

    }
    private void reset(){
        ChoboxVisiblity(otherChoiceBoxVbox,otherChoiceBoxHboxList,otherChoiceBoxList,false);

        setChoiceBoxDisability(true,new ArrayList<>(List.of(StatusChoiceBox,hardSizeChoiceBox,ramSizeChoiceBox,OSChoiseBox)));
        setChoiceBoxDisability(true,inputChoiceBoxList);
        setChoiceBoxDisability(true,outputChoiceBoxList);
        setChoiceBoxDisability(true,otherChoiceBoxList);

        otherDetailVbox.setVisible(false);
        setEditable(new ArrayList<>(List.of(regNumTextField,modelTextField,other5,other6)),false,"grey");
        setEditable(otherTextList,false,"grey");
        setEditable(userTextLsit,false,"grey");

        inputVbox.setVisible(false);
        outputVbox.setVisible(false);

        userDetailsVbox.setVisible(false);
        for(HBox h:otherHboxList){
            h.setVisible(false);
        }
        for(HBox input:inputHboxList){
            input.setVisible(false);
        }
        saveBtn.setDisable(true);
        resetBtn.setDisable(true);
    }
    @FXML
    private void cancel(){
        reset();
        showDeviceDetail();
    }
    @FXML
    private void save(){
        String loggedUser=newInstance.getLogedUser();
        boolean isDbAdded=addDb();
        if(isDbAdded){
         //   otherErrorLogger.info("user "+loggedUser+" updated a new device / values:"+newValues);
            alert(Alert.AlertType.INFORMATION,"Success","Successfully updated device \n"+newValues);
            //after saving set non editable the field
            setEditable(new ArrayList<>(List.of(modelTextField,other5,other6)),false,"grey");
            setEditable(otherTextList,false,"grey");
        }
        else {
            //otherErrorLogger.info("user "+loggedUser+" try to update the device with values:"+newValues);
        }
        showDeviceDetail();
        newValues.clear();
    }
    @FXML
    public void Remove(ActionEvent e) throws IOException {
        Devices dev[];
        deviceInfoCardController=new DeviceInfoCardController();
        // Create custom ButtonType instances
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        String OtherDev=getOtherDevCat();

        // Create a confirmation alert with custom buttons
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?", yesButton, noButton);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Needed");

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            switch (deviceSelector) {
                case "Desktop" -> {
                    Desktop instance=Desktop.getDesktopInstance();
                    instance.deleteDevice(getDevRegNum(),"DesRegNum","desktop");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);;
                }
                case "Photocopy Machines" ->{
                    PhotocpyMchine instance=PhotocpyMchine.getPhotocpyMchineInstance();
                    instance.deleteDevice(getDevRegNum(),"PhotoCopyMachineRegNum","PhotoCopyMachine");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    break;
                }
                case "Monitors" ->{
                    Monitors instnace=Monitors.getMonitorInstance();
                    instnace.deleteDevice(getDevRegNum(),"MonitorRegNum","monitor");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    break;
                }
                case "Projectors" -> {
                    Projectors instance=Projectors.getProjectorsInstance();
                    instance.deleteDevice(getDevRegNum(),"MultimediaProjectorRegNum","multimediaprojector");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    break;
                }
                case "Laptops" -> {
                    Laptops instance=Laptops.getLaptopsInstance();
                    instance.deleteDevice(getDevRegNum(),"LaptopRegNum","laptop");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    break;
                }
                case "Printers" -> {
                    Printer instance=Printer.getPrinterInstance();
                    instance.deleteDevice(getDevRegNum(),"PrinterRegNum","printer");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    break;

                }
                case "UPS" -> {
                    UPS instance=UPS.getUpsInstance();
                    instance.deleteDevice(getDevRegNum(),"upsRegNum","ups");
                    otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    break;
                }

                default -> {
                    if(deviceSelector.equals(OtherDev)){
                        OtherDevices instance=OtherDevices.getOtherDevicesInstance();
                        instance.deleteDevice(getDevRegNum(),getOtherDevCat()+"RegNum",getOtherDevCat());
                        otherErrorLogger.info("/"+newInstance.getLogedUser() + "/remove a device  "+"/Device RegNumber:"+getDevRegNum()+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                    }
                    else{
                        throw new IllegalStateException("Unexpected value: " + deviceSelector);
                    }

                }
            }
            if(isLoadFromQuick()){
                setLoadFromQuick(false);
                regNumTextField.setText("");
                modelTextField.setText("");
                StatusChoiceBox.setValue("");

                reset();
            }else{
                getDashboardPathFinderControllerD().popSceneStack();
                getDashboardPathFinderControllerD().goBack(e);
            }

        } else {
            alert.close();
        }

    }
    private boolean addDb() {
        switch (deviceSelector) {
            case "Desktop" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(other5.getText() + " " + hardSizeChoiceBox.getValue());//get hard disk
                newValues.add(other6.getText() + " " + ramSizeChoiceBox.getValue());//get ram
                newValues.add(OSChoiseBox.getValue());//get os
                getChoiceBoxValue(otherChoiceBoxList);
                getChoiceBoxValue(inputChoiceBoxList);
                getChoiceBoxValue(outputChoiceBoxList);
                newValues.add(getDevRegNum());

                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return Desktop.getDesktopInstance().updateDevice(newValues);

            }
            case "Photocopy Machines" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(getDevRegNum());

                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return PhotocpyMchine.getPhotocpyMchineInstance().updateDevice(newValues);
            }
            case "Monitors" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(getDevRegNum());

                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return Monitors.getMonitorInstance().updateDevice(newValues);


            }
            case "Projectors" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(getDevRegNum());

                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return Projectors.getProjectorsInstance().updateDevice(newValues);

            }
            case "Laptops" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(other5.getText() + " " + hardSizeChoiceBox.getValue());//get hard disk
                newValues.add(other6.getText() + " " + ramSizeChoiceBox.getValue());//get ram
                newValues.add(OSChoiseBox.getValue());//get os
                getChoiceBoxValue(inputChoiceBoxList);
                newValues.add(getDevRegNum());

                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return Laptops.getLaptopsInstance().updateDevice(newValues);

            }
            case "Printers" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(getDevRegNum());
                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return Printer.getPrinterInstance().updateDevice(newValues);
            }
            case "UPS" -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(getDevRegNum());

                otherErrorLogger.info("/"+newInstance.getLogedUser() + "/update details of a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+deviceSelector);
                return UPS.getUpsInstance().updateDevice(newValues);
            }
            default -> {
                getTextFieldText(new ArrayList<>(List.of(modelTextField)));
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(getDevRegNum());
                return OtherDevices.getOtherDevicesInstance().updateDevice(otherDevCat,newValues);
            }
        }
    }
    private void alert(Alert.AlertType alertType, String title, String content){
        Alert alert=new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }


    /*------------------------Interactions with DeviceUser TABLE-----------------------------------*/
    @FXML
    private void editUser(){
        setEditable(userTextLsit,true,"#03AED2");
    }
    private boolean nicFieldCheck(){
        return !Objects.equals(userNIC.getText(), "")||Objects.equals(userNIC.getText(), "No user");
    }
    @FXML
    private void assignUser(){
        if(!userNIC.getText().equals(initialUser)){
            if(DeviceUser.getDeviceUserInstance().isNicAvailable(userNIC.getText())==null) {
                //add new deviceUser to the deviceUser table
                DeviceUser.getDeviceUserInstance().insertUser(new ArrayList<>(List.of(userNIC.getText(), userName.getText(), userTitle.getText(), userGmail.getText())));
            }
            switch (deviceSelector){
                case "Desktop"->{
                    //add new deviceUser to the desktop table
                    Desktop.getDesktopInstance().updateDeviceUser(userNIC.getText(),getDevRegNum());
                   // otherErrorLogger.info("new user "+userNIC.getText()+" assign to a device / RegNo:"+ getDevRegNum());
                }
                case "Laptops"->{
                    //add new deviceUser to the laptop table
                    Laptops.getLaptopsInstance().updateDeviceUser(userNIC.getText(),getDevRegNum());
                  //  otherErrorLogger.info("new user "+userNIC.getText()+" assign to a device / RegNo:"+ getDevRegNum());
                }
            }

        }
        setEditable(userTextLsit,false,"grey");
    }
    private void userDetails(String nic){
        userDetailsVbox.setVisible(true);
        if(nic==null){
            userNIC.setText("No user");
        }
        else{
            userNIC.setText(nic);
        }

        initialUser=nic;
    }

}
