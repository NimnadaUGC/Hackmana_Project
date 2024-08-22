package org.example.hakmana.view.dialogBoxes;

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
import org.example.hakmana.view.scene.DeviceMngmntSmmryScene;
import org.example.hakmana.view.scene.LoginPageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddDeviceDialogController implements Initializable {
    private static final Logger otherErrorLogger= (Logger) LogManager.getLogger(AddDeviceDialogController.class);
    private static AddDeviceDialogController instance=null;
    @FXML
    public ChoiceBox<String> devCat;//devcat selector
    private String devCategoryName;
    private String devRegNum;

    //Device details
    //common
    @FXML
    public TextField modelTextField;
    @FXML
    public TextField regNumTextField;
    @FXML
    public ChoiceBox<String> StatusChoiceBox;


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

    //Hard disk
    @FXML
    public HBox other5Hbox;
    @FXML
    public TextField other5;
    @FXML
    public Label other5Lbl;
    @FXML
    public ChoiceBox<String> hardSizeChoiceBox;

    //Ram
    @FXML
    public HBox other6Hbox;
    @FXML
    public TextField other6;
    @FXML
    public ChoiceBox<String> ramSizeChoiceBox;

    //Operating system
    @FXML
    public HBox other7Hbox;
    @FXML
    public ChoiceBox<String> OSChoiseBox;

    //All Other Choice box
    @FXML
    public VBox otherChoiceBoxVbox;
    @FXML
    public HBox other8Hbox;
    @FXML
    public ChoiceBox<String> FloppyDiskChoiseBox;
    @FXML
    public HBox other9Hbox;
    @FXML
    public ChoiceBox<String> SoundCardChoiseBox;
    @FXML
    public HBox other10Hbox;
    @FXML
    public ChoiceBox<String> TVCardChoiseBox;
    @FXML
    public HBox other11Hbox;
    @FXML
    public ChoiceBox<String> NetworkCardChoiseBox;
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
    public TextField userNIC;
    @FXML
    public TextField userTitle;
    @FXML
    public TextField userName;
    @FXML
    public TextField userGmail;

    @FXML
    public HBox interactionHbox;
    @FXML
    public Button submitButton;
    @FXML
    public Button resetBtn;
    @FXML
    public Button addUserButton;
    LoginPageController newInstance=LoginPageController.getInstance();
    //Array fo populate the choiceBoxes
    private final String[] devCategories={"Desktop","Photocopy Machines","Monitors","Projectors","Laptops","Printers","UPS"};
    private final String[] deviceStatus={"Active","Repairing","Inactive","Not Assigned"};
    private final String[] YN={"Yes","No"};
    private final String[] WinLin={"Windows","Linux"};
    private final String[] OnboardDecicated={"On Board","Dedicated","No"};
    private final String[] sizeTypeList={"MB","GB","TB"};


    private boolean isFromComponent;

    public static DeviceUser deviceUser;

    //for get new values from the textFields
    private ArrayList<String> newValues=new ArrayList<>();

    /*----------------------------------------------------------------------------*/
    AddDeviceDialogController(){

    }
    public static AddDeviceDialogController getInstance() {
        if(instance==null){
            instance=new AddDeviceDialogController();
            return instance;
        }
        return instance;
    }

    /*-------------------------------Initialize---------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isFromComponent=false;
        deviceUser =DeviceUser.getDeviceUserInstance();

        devCat.getItems().addAll(devCategories);
        StatusChoiceBox.getItems().addAll(deviceStatus);
        OSChoiseBox.getItems().addAll(WinLin);

        populateChoiceBx();

        //get all the other details vbox, label, Hboxes, TextField, ChoiceBoxes
        otherHboxList=new ArrayList<>(List.of(other1Hbox,other2Hbox,other3Hbox,other4Hbox));
        otherLblList=new ArrayList<>(List.of(other1Lbl,other2Lbl,other3Lbl,other4Lbl));
        otherTextList=new ArrayList<>(List.of(other1,other2,other3,other4));
        otherChoiceBoxHboxList=new ArrayList<>(List.of(other8Hbox,other9Hbox,other10Hbox,other11Hbox,
                other12Hbox,other13Hbox,other14Hbox,other15Hbox));
        otherChoiceBoxList=new ArrayList<>(List.of(FloppyDiskChoiseBox,
                SoundCardChoiseBox,TVCardChoiseBox,NetworkCardChoiseBox,
                SsdChoiceBox,CdRomChoiceBox,UpsChoiceBox,PowerSupplyChoiceBox));

        //get all the input vbox, label, Hboxes and ChoiceBoxes
        inputHboxList=new ArrayList<>(List.of(input1Hbox,input2Hbox,input3Hbox,input4Hbox));
        inputLblList=new ArrayList<>(List.of(input1Lbl,input2Lbl,input3Lbl,input4Lbl));
        inputChoiceBoxList=new ArrayList<>(List.of(inputChoiceBox1,inputChoiceBox2,inputChoiceBox3,inputChoiceBox4));

        //get all the output vbox, label, Hboxes and Choiceboxes
        outputHboxList=new ArrayList<>(List.of(output1Hbox,output2Hbox,output3Hbox));
        outputLblList=new ArrayList<>(List.of(output1Lbl,output2Lbl,output3Lbl));
        outputChoiceBoxList=new ArrayList<>(List.of(outputChoiceBox1,outputChoiceBox2,outputChoiceBox3));

        //get all the deviceUser textfield
        userTextLsit=new ArrayList<>(List.of(userNIC,userName,userTitle,userGmail));

        resetBtnAction();

        //update the register number field realtime
        regNumTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            //Check the given regNUm is available

            // Enable the submitButton only if regNumTextField is not empty
            submitButton.setDisable(newValue.isEmpty());

            //set editable textfield when deviceUser enter register number
            setEditable(new ArrayList<>(List.of(modelTextField)),true,"#03AED2");
            setEditable(otherTextList,true,"#03AED2");
            setEditable(userTextLsit,true,"#03AED2");

            other5.setEditable(true);//hard disk editable
            other6.setEditable(true);//ram make editable
            other5.setStyle("-fx-border-color: #03AED2;-fx-border-width: 2;-fx-border-radius: 5");
            other6.setStyle("-fx-border-color: #03AED2;-fx-border-width: 2;-fx-border-radius: 5");

            //set choice box enable
            StatusChoiceBox.setDisable(false);
            OSChoiseBox.setDisable(false);
            hardSizeChoiceBox.setDisable(false);
            ramSizeChoiceBox.setDisable(false);

            setChoiceBoxDisability(false,otherChoiceBoxList);
            setChoiceBoxDisability(false,inputChoiceBoxList);
            setChoiceBoxDisability(false,outputChoiceBoxList);
            setDevRegNum(newValue);

        });

        //Realtime get the Users
        userNIC.textProperty().addListener((observable, oldValue, newValue) -> {
            // Enable the submitButton only if regNumTextField is not empty
            addUserButton.setDisable(newValue.isEmpty());

            // Check if the newValue is available in the users array
            DeviceUser deviceUser =DeviceUser.getDeviceUserInstance().isNicAvailable(newValue);
            if (deviceUser != null) {
                // Autofill the other text fields
                userGmail.setText(deviceUser.getGmail());
                userName.setText(deviceUser.getName());
                userTitle.setText(deviceUser.getTitle());
            }
        });

        //update the device category selecting choicebox realtime
        devCat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            listnerReset();
            setDevCategoryName(newValue);
            setView();
        });

        // Disable the submitButton initially if regNumTextField is empty
        submitButton.setDisable(regNumTextField.getText().isEmpty());
        addUserButton.setDisable(regNumTextField.getText().isEmpty());
    }


    /*----------------------Getters and Setters----------------------*/
    public String getDevCategoryName() {
        return devCategoryName;
    }
    public void setDevCategoryName(String devCategoryName) {
        this.devCategoryName = devCategoryName;
    }
    public String getDevRegNum() {
        return devRegNum;
    }
    public void setDevRegNum(String devRegNum) {
        this.devRegNum = devRegNum;
    }
    public void setDevCat() {
        devCat.setValue("Select a device");
    }
    //set the text fields editing ability and border color
    private void setEditable(ArrayList<TextField> textFieldslist, boolean setEdit, String color){
        for(TextField textField:textFieldslist){
            textField.setEditable(setEdit);
            textField.setStyle("-fx-border-color: "+color+";-fx-border-width: 2;-fx-border-radius: 5");
        }
    }
    //populate choice boxes (input choice boxes,output choice boxes,ups,powersupply)
    private void setChoBox(ArrayList<String> items,ChoiceBox<String> choBox) {
        if(choBox.getItems().isEmpty()){
            choBox.getItems().addAll(items);
        }
        choBox.getItems().add("No");
    }
    //populate the choice boxes(floopydisk,cdrom,networkcard,soundcard,tvcard,ssd,cd)
    private void populateChoiceBx(){
        FloppyDiskChoiseBox.getItems().addAll(YN);
        CdRomChoiceBox.getItems().addAll(YN);

        NetworkCardChoiseBox.getItems().addAll(OnboardDecicated);
        SoundCardChoiseBox.getItems().addAll(OnboardDecicated);
        TVCardChoiseBox.getItems().addAll(OnboardDecicated);

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

    private void setView(){
        ChoboxVisiblity(otherChoiceBoxVbox,otherChoiceBoxHboxList,otherChoiceBoxList,false);
        switch (getDevCategoryName()) {
            case "Desktop" -> {
                Desktop desktop=Desktop.getDesktopInstance();
                setOtherDetails(new String[]{"Serial Number","Purchased Form","Processor"});
                other5Hbox.setVisible(true);//hard disk
                acceptOnlyNumbers(other5);
                acceptOnlyNumbers(other6);
                other5Lbl.setText("Hard Disk");
                other6Hbox.setVisible(true);//ram
                other7Hbox.setVisible(true);//os
                setOutputDetails(new String[]{"Monitor Register Number","Speaker Register Number","Printer Register Number"});
                setInputDetails(new String[]{"Mouse Register Number","Keyboard Register Number","Mic Register Number","Scanner Register Number"});
                userDetailsVbox.setVisible(true);

                ChoboxVisiblity(otherChoiceBoxVbox,otherChoiceBoxHboxList,otherChoiceBoxList,true);
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

            }
            case "Photocopy Machines", "Projectors" ->
                setOtherDetails(new String[]{"Purchased From"});
            case "Monitors" -> {
                setOtherDetails(new String[]{"Purchased From","Screen Size"});
                acceptOnlyNumbers(other2);
            }
            case "Laptops" -> {
                Laptops laptop=Laptops.getLaptopsInstance();
                setOtherDetails(new String[]{"Purchased From","CPU"});
                setInputDetails(new String[]{"Mouse Register Number","Keyboard Register Number"});
                other5Hbox.setVisible(true);//hard disk
                other5Lbl.setText("Storage");
                other6Hbox.setVisible(true);//ram
                other7Hbox.setVisible(true);//os
                userDetailsVbox.setVisible(true);

                //populate the choice boxes
                hardSizeChoiceBox.getItems().addAll(sizeTypeList);
                ramSizeChoiceBox.getItems().addAll(sizeTypeList);
                setChoBox(laptop.getMousesRegNum(),inputChoiceBox1);
                setChoBox(laptop.getKeyboardsRegNum(),inputChoiceBox2);
            }
            case "Printers" -> {
                setOtherDetails(new String[]{"purchased From","Serial Number","Paper Input","Paper Output"});
                acceptOnlyNumbers(other3);
                acceptOnlyNumbers(other4);
            }
            case "UPS" ->
                setOtherDetails(new String[]{"purchasedFrom"});
            default -> {
                setOtherDetails(new String[]{"purchasedFrom"});
            }
        }
    }
    private void setOtherDetails(String[] otherlblText){
        otherDetailVbox.setVisible(true);
        for(int i=0;i< otherlblText.length;i++){
            otherHboxList.get(i).setVisible(true);
            otherTextList.get(i).setVisible(true);
            otherLblList.get(i).setText(otherlblText[i]);
        }
    }
    private void setInputDetails(String[] inputLblText){
        inputVbox.setVisible(true);
        for(int i=0;i< inputLblText.length;i++){
            inputHboxList.get(i).setVisible(true);
            inputChoiceBoxList.get(i).setVisible(true);
            inputLblList.get(i).setText(inputLblText[i]);
        }
    }
    private void setOutputDetails(String[] outputLblText){
        outputVbox.setVisible(true);
        for(int i=0;i< outputLblText.length;i++){
            outputHboxList.get(i).setVisible(true);
            outputChoiceBoxList.get(i).setVisible(true);
            outputLblList.get(i).setText(outputLblText[i]);
        }

    }


    /*--------------------------Interaction------------------------------------*/
    private boolean checkCat(){
        if(Objects.equals(devCat.getValue(), "Select a device")){
            alert(Alert.AlertType.WARNING,"No Category","Please select a category first");
            return false;
        }
        return true;
    }
    private boolean addDb(){
        switch (getDevCategoryName()) {
            case "Desktop" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(other5.getText()+" "+hardSizeChoiceBox.getValue());//get hard disk
                newValues.add(other6.getText()+" "+ramSizeChoiceBox.getValue());//get ram
                newValues.add(OSChoiseBox.getValue());//get os
                getChoiceBoxValue(otherChoiceBoxList);
                getChoiceBoxValue(inputChoiceBoxList);
                getChoiceBoxValue(outputChoiceBoxList);
                if(nicFieldCheck()) {
                    newValues.add(userNIC.getText());
                    addUser();
                }else{
                    newValues.add(null);
                }
                return Desktop.getDesktopInstance().insertDevice(newValues);

            }
            case "Photocopy Machines" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                return PhotocpyMchine.getPhotocpyMchineInstance().insertDevice(newValues);
            }
            case "Monitors" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                return Monitors.getMonitorInstance().insertDevice(newValues);

            }
            case "Projectors" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);

                return Projectors.getProjectorsInstance().insertDevice(newValues);

            }
            case "Laptops" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                newValues.add(other5.getText()+" "+hardSizeChoiceBox.getValue());//get storage disk
                newValues.add(other6.getText()+" "+ramSizeChoiceBox.getValue());//get ram
                newValues.add(OSChoiseBox.getValue());//get os
                getChoiceBoxValue(inputChoiceBoxList);

                if(nicFieldCheck()) {
                    newValues.add(userNIC.getText());
                    addUser();
                }else{
                    newValues.add(null);
                }

                return Laptops.getLaptopsInstance().insertDevice(newValues);

            }
            case "Printers" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);

                return Printer.getPrinterInstance().insertDevice(newValues);

            }
            case "UPS" -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);

                return UPS.getUpsInstance().insertDevice(newValues);

            }
            default -> {
                newValues.add(getDevRegNum());
                newValues.add(modelTextField.getText());
                newValues.add(StatusChoiceBox.getValue());
                getTextFieldText(otherTextList);
                
                return OtherDevices.getOtherDevicesInstance().insertDevice(getDevCategoryName(),newValues);
            }
        }
    }
    @FXML
    public void submitButtonOnAction() {
        String loggedUser=newInstance.getLogedUser();
        boolean isCatSelected=checkCat();
        boolean isDbAdded=false;
        if(isCatSelected){
            isDbAdded=addDb();

        }
        if(isCatSelected && isDbAdded){
            otherErrorLogger.info("/"+newInstance.getLogedUser() + "/add a device  "+"/Device RegNumber:"+getDevRegNum()+""+", Brand:"+newValues.get(0)+",Status:"+newValues.get(1)+"/"+getDevRegNum()+"/"+newInstance.getLogedUser()+"/"+getDevCategoryName());
            alert(Alert.AlertType.INFORMATION,"Success","Successfully inserted new device \n"+newValues);
            resetBtnAction();
        }
        if(isFromComponent) {
            DeviceMngmntSmmryScene.getInstance().updateUI();//when device added update ui
        }
        newValues.clear();
    }

    // Reset the form into general form according to the event listener
    private void listnerReset(){
        clearAll();//clearAll the fields

        //disable all the choice boxes
        StatusChoiceBox.setDisable(true);
        OSChoiseBox.setDisable(true);
        setChoiceBoxDisability(true,otherChoiceBoxList);
        setChoiceBoxDisability(true,inputChoiceBoxList);
        setChoiceBoxDisability(true,outputChoiceBoxList);
        hardSizeChoiceBox.setDisable(true);
        ramSizeChoiceBox.setDisable(true);

        //set text field to non-editable and colors
        setEditable(new ArrayList<>(List.of(modelTextField)), false, "grey");
        setEditable(otherTextList, false, "grey");
        setEditable(userTextLsit, false, "grey");
        other5.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 5");
        other6.setStyle("-fx-border-color: grey;-fx-border-width: 2;-fx-border-radius: 5");

        //disable the addUserButton
        addUserButton.setDisable(true);

        if(!isFromComponent) {
            other5Hbox.setVisible(false);//hard disk
            other5.setEditable(false);
            other6Hbox.setVisible(false);//ram
            other6.setEditable(false);
            other7Hbox.setVisible(false);//os
            //except the common vbox set all other fields to not visible
            otherDetailVbox.setVisible(false);
            for (HBox otherHbox : otherHboxList) {
                otherHbox.setVisible(false);
            }
            for(TextField otherText:otherTextList){
                otherText.setVisible(false);
            }
            ChoboxVisiblity(inputVbox,inputHboxList,inputChoiceBoxList,false);
            ChoboxVisiblity(outputVbox,outputHboxList,outputChoiceBoxList,false);
            userDetailsVbox.setVisible(false);

        }
    }
    //handle reset button action
    @FXML
    public void resetBtnAction(){
        listnerReset();
        if(!isFromComponent){
            setDevCat();
        }
    }
    //clear all  the text field and choice box values
    private void clearAll(){
        StatusChoiceBox.getSelectionModel().clearSelection();
        clearChoiceBox(otherChoiceBoxList);
        clearChoiceBox(inputChoiceBoxList);
        clearChoiceBox(outputChoiceBoxList);
        hardSizeChoiceBox.getSelectionModel().clearSelection();
        ramSizeChoiceBox.getSelectionModel().clearSelection();

        other5.clear();
        other6.clear();
        regNumTextField.clear();
        modelTextField.clear();
        for(TextField fields:otherTextList){
            fields.clear();
        }
        for(TextField fields:userTextLsit){
            fields.clear();
        }

    }
    private void alert(Alert.AlertType alertType,String title,String content){
        Alert alert=new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }


    /*---------------------Create Configurations--------------------*/

    //According to the device selected in DeviceMngmntSmmryScene load the view
    //disable the devCat dropdown box
    public void setCardForm(String cat){
        devCat.setValue(cat);
        devCat.setDisable(true);
        setDevCategoryName(cat);
        isFromComponent=true;
        setView();
    }
    //clear all the Choice boxes values
    private void clearChoiceBox(ArrayList<ChoiceBox<String>> choiceBoxList){
        for(ChoiceBox<String> choBox:choiceBoxList){
            choBox.getSelectionModel().clearSelection();
        }
    }

    //get the Choice Box values and add to the newValues arraylist(except Status Choice Box)
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
    //get the text fields values
    private void getTextFieldText(ArrayList<TextField> textFieldslists){
        for(TextField textField:textFieldslists){
            if(textField.getText().isEmpty() && textField.isVisible()){
                //alert(Alert.AlertType.CONFIRMATION,"Confirm","Empty Field detected\nDo you want to continue");
                newValues.add(null);
            }
            else if(textField.isVisible()){
                newValues.add(textField.getText());
            }

        }
    }

    /*------------------------Interactions with DeviceUser TABLE-----------------------------------*/
    private boolean nicFieldCheck(){
        return !Objects.equals(userNIC.getText(), "");
    }
    @FXML
    private void addUser(){
        if(nicFieldCheck()) {
            if(deviceUser.isNicAvailable(userNIC.getText()) == null) {
                //add new deviceUser to the deviceUser table
                deviceUser.insertUser(new ArrayList<>(List.of(userNIC.getText(), userName.getText(), userTitle.getText(), userGmail.getText())));
                otherErrorLogger.info("new deviceUser is added / user:"+userNIC.getText());
            }
        }else {
            alert(Alert.AlertType.WARNING,"No User","Please select a device user");
        }
    }

    
}
