package org.example.hakmana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DesktopFormController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    private ChoiceBox<String> StatusChoiseBox;
    private String[] deviceStatus={"Active","Repairing","Inactive"};
    public void addUser(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene/user.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void desktop(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("desktop.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StatusChoiseBox.getItems().addAll(deviceStatus);
    }
}
