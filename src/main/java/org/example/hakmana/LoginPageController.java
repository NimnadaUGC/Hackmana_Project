package org.example.hakmana;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.hakmana.model.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DatabaseConnection databaseConnection;
    private String query;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @FXML
    private TextField psswrdFeild,usrNameFeild;

    @FXML
    private Button login;

    @FXML
    private CheckBox remenberCheckBox;

    public static String cuurentUser="";


    public void DashboardSceneLoad(ActionEvent event) throws IOException {
        String tempUserName = usrNameFeild.getText();
        String tempPsswrd = sha1(psswrdFeild.getText()); // Hash the input password using SHA-1

        try {
            // Query to retrieve user information
            query = "SELECT * FROM systemUser WHERE userName = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tempUserName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("pwd");

                if (tempPsswrd.equals(storedPassword)) {
                    // Passwords match, load dashboard
                    loadDashboard(event);

                    cuurentUser = tempUserName;

                    if (remenberCheckBox.isSelected()) {
                        query = "UPDATE systemUser SET isRemember = TRUE WHERE userName = ?";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, tempUserName);
                    } else {
                        query = "UPDATE systemUser SET isRemember = FALSE;";
                        preparedStatement = connection.prepareStatement(query);
                    }
                    preparedStatement.execute();
                } else {
                    alertBox(event, "Password Incorrect", "You have entered an incorrect password.");
                }
            } else {
                alertBox(event, "Username Incorrect", "The username you entered does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection errors
            alertBox(event, "Error", "System Error!!!");
        }
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
            e.printStackTrace();
            return null;
        }
    }




    // Method to load dashboard scene
    private void loadDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        Screen screen = Screen.getPrimary();
        double width = screen.getBounds().getWidth();
        double height = screen.getBounds().getHeight();

        stage.setWidth(width);
        stage.setHeight(height);

        stage.setX(0.0);
        stage.setY(0.0);
        stage.setScene(scene);
        stage.show();
        System.out.println("Login successfull");
    }

    static void alertBox(ActionEvent event,String title,String content){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection=DatabaseConnection.getInstance();
        connection =databaseConnection.getConnection();
        query = "SELECT userName, pwd FROM systemUser WHERE isRemember = TRUE;";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storeUserName = resultSet.getString("userName");
               // String storedPassword = resultSet.getString("pwd");
               // psswrdFeild.setText(storedPassword);
                usrNameFeild.setText(storeUserName);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

}








