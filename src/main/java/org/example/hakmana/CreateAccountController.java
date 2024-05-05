package org.example.hakmana;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.hakmana.model.DatabaseConnection;
import org.example.hakmana.model.SystemUser;


import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccountController {

    @FXML
    public TextField userNameTxt;
    @FXML
    public TextField fullNameTxt;
    @FXML
    public TextField postTxt;
    @FXML
    public TextField empIdTxt;
    @FXML
    public PasswordField pwdText;
    @FXML
    public TextField emailTxt;
    @FXML
    public TextField phoneNumTxt;

    private DatabaseConnection databaseConnection;
    private Connection connection;


    static void alertBox(ActionEvent event,String title,String content){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void clearFields() {
        userNameTxt.setText("");
        fullNameTxt.setText("");
        postTxt.setText("");
        empIdTxt.setText("");
        pwdText.setText("");
        emailTxt.setText("");
        phoneNumTxt.setText("");

    }

    public void createAccButtonOnAction(ActionEvent actionEvent) {
        if(!(userNameTxt.getText().equals("")||fullNameTxt.getText().equals("")||postTxt.getText().equals("")||pwdText.getText().equals("")||phoneNumTxt.getText().equals(""))){
            databaseConnection=DatabaseConnection.getInstance();
            connection=databaseConnection.getConnection();

            SystemUser user=new SystemUser(userNameTxt.getText(),fullNameTxt.getText(),postTxt.getText(),empIdTxt.getText(),pwdText.getText(),emailTxt.getText(),phoneNumTxt.getText());

            // SQL query with placeholders
            String sql = "INSERT INTO systemUser (userName, fullName, post, empId, pwd, email, phoneNum) VALUES (?, ?, ?, ?, SHA1(?), ?, ?)";



            try {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, userNameTxt.getText());
                preparedStatement.setString(2, fullNameTxt.getText());
                preparedStatement.setString(3, postTxt.getText());
                preparedStatement.setString(4, empIdTxt.getText());
                preparedStatement.setString(5, pwdText.getText());
                preparedStatement.setString(6, emailTxt.getText());
                preparedStatement.setString(7, phoneNumTxt.getText());


                // Execute the PreparedStatement
                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    alertBox(actionEvent,"","A new user was inserted successfully!");
                    System.out.println("A new user was inserted successfully!");
                } else {
                    System.out.println("Failed to insert the user.");
                    alertBox(actionEvent,"Error","Failed to insert the user!");
                }
                clearFields();
            } catch (SQLException e) {
                alertBox(actionEvent,"Error","System Error!!");
                e.printStackTrace();
            }

        }else{
            alertBox(actionEvent,"Error","You Should Enter All Required Fields!");
        }

    }
}
