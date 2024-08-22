package org.example.hakmana.view.scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.AllDeviceDetails;
import org.example.hakmana.model.userMngmnt.SystemUser;
import org.example.hakmana.view.component.AddDevButtonController;
import org.example.hakmana.view.dialogBoxes.ForgotPasswrdDialog;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    private static final Logger otherErrorLogger= (Logger) LogManager.getLogger(LoginPageController.class);
    public static String curentUser = "";

    private static LoginPageController instance=null;
    public Text forgotBtn;
    private String query;
    @FXML
    private TextField usrNameFeild;
    @FXML
    private PasswordField psswrdFeild;
    @FXML
    private Button login;
    @FXML
    private CheckBox remenberCheckBox;
    private String logedUser;
    private LoginPageController(){}

    public static LoginPageController getInstance() {
        if(instance==null){
            instance=new LoginPageController();
            return instance;
        }
        return instance;
    }

    // Method to hash the password using SHA-1
    private static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the NoSuchAlgorithmException
            otherErrorLogger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    static void alertBox(ActionEvent event, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void DashboardSceneLoad(ActionEvent event) throws IOException {
        String tempUserName = usrNameFeild.getText();
        String tempPsswrd = sha1(psswrdFeild.getText()); // Hash the input password using SHA-1
        SystemUser systemUser = new SystemUser();

        String storedPassword = systemUser.getPassword(tempUserName);

        if (storedPassword != null) {
            if (tempPsswrd.equals(storedPassword)) {
                // Passwords match, load dashboard
                loadDashboard(event);
                logedUser=tempUserName;
                otherErrorLogger.info(tempUserName+" is Successfully logged in");
                curentUser = tempUserName;
                systemUser.setUserName(tempUserName);

                if (remenberCheckBox.isSelected()) {
                    query = "UPDATE systemUser SET isRemember = TRUE WHERE userName = ?";
                    systemUser.setRemember(true);
                    systemUser.dbIsRemember();
                } else {
                    query = "UPDATE systemUser SET isRemember = FALSE;";
                    systemUser.setRemember(false);
                    systemUser.dbIsRemember();
                }
            } else {
                alertBox(event, "Password Incorrect", "You have entered an incorrect password.");
            }
        } else {
            alertBox(event, "Username Incorrect", "The username you entered does not exist.");
        }
    }

    // Method to load dashboard scene
    private void loadDashboard(ActionEvent event) throws IOException {
        FXMLLoader dasboardFxmlLoader=new FXMLLoader();

        DashboardController dashboardController=DashboardController.getInstance();
        dasboardFxmlLoader.setController(dashboardController);
        dasboardFxmlLoader.setLocation(org.example.hakmana.view.scene.DashboardController.class.getResource("Dashboard.fxml"));

        Parent root = dasboardFxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        Screen screen = Screen.getPrimary();
        double width = screen.getBounds().getWidth()-100;
        double height = screen.getBounds().getHeight()-100;

        stage.setResizable(true);
        dashboardController.setWindowStage(stage);
        stage.setWidth(width);
        stage.setHeight(height);

        stage.setX(0.0);
        stage.setY(0.0);
        stage.setScene(scene);
        stage.show();
    }
    public String getLogedUser(){
        return logedUser;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SystemUser systemUser = new SystemUser();
        String storeUserName = systemUser.getIsRemUName();
        usrNameFeild.setText(storeUserName);

        // Adding focus listeners to provide visual cues
        usrNameFeild.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usrNameFeild.setStyle("-fx-border-color: blue;");
            } else {
                usrNameFeild.setStyle("");
            }
        });

        psswrdFeild.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                psswrdFeild.setStyle("-fx-border-color: blue;");
            } else {
                psswrdFeild.setStyle("");
            }
        });

        // Bind the ENTER key to switch focus from Username to Password field
        usrNameFeild.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    psswrdFeild.requestFocus(); // Switch focus to Password field
                }
            }
        });

        // Bind the ENTER key to the login button in Password field
        psswrdFeild.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login.fire(); // Fire the login button's action when ENTER is pressed
                }
            }
        });

        // Bind the ENTER key to the button
        login.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login.fire(); // Fire the button's action when ENTER is pressed
                }
            }
        });
    }

    public void forgotPsswrdDialogPane(MouseEvent event) throws IOException {
        ForgotPasswrdDialog forgotPasswrdController = ForgotPasswrdDialog.getInstance();
        FXMLLoader forgotFxmlLoad = new FXMLLoader();
        forgotFxmlLoad.setLocation(org.example.hakmana.view.dialogBoxes.ForgotPasswrdDialog.class.getResource("FrogotPasswrd.fxml"));
        forgotFxmlLoad.setController(forgotPasswrdController);
        DialogPane forgotDialogPane = forgotFxmlLoad.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(forgotDialogPane);
        dialog.setTitle("Forgot password");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.CANCEL) {
            dialog.close();
        }
    }

}
