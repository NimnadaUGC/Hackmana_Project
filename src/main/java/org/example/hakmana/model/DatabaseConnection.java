package org.example.hakmana.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DatabaseConnection {
    private static DatabaseConnection instance =null;
    private Connection connection;
    private static final Logger sqlLogger= (Logger) LogManager.getLogger(DatabaseConnection.class);
    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hakmanaedm", "root", "@");
            System.out.println("Connection Successfully");
        } catch (ClassNotFoundException | SQLException e) {
            sqlLogger.error("connection is null");
            System.out.println("Connection failed");
            //Need to show this alert
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Unable to establish the connection");
        }
    }

    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance= new DatabaseConnection();
            return instance;
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeSt(String sqlSt) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSt);
            // Execute the SQL query and get the result set
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            sqlLogger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public Optional<ButtonType> alerting(Alert.AlertType alertType, String title, String header, String content){
        Alert alert=new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public ArrayList<String> quickAccessLoadDevRegnum(String selDevice){
        String colName = selDevice+"RegNum";
        if(selDevice== "Desktop"){
            colName="DesRegNum";
        }
        if (selDevice== "Photocopy Machine") {
            selDevice="PhotoCopyMachine";
            colName=selDevice+"RegNum";
        }
        if (selDevice== "Projectors") {
            selDevice="MultimediaProjector";
            colName=selDevice+"RegNum";
        }
        String stringForDeviceRegNum="Select * From " +selDevice;
        ArrayList<String> regNumbers=new ArrayList<>();
        try(ResultSet rs = executeSt(stringForDeviceRegNum)){
            while(rs.next()){
                regNumbers.add(rs.getString(colName));
            }
        }catch (SQLException e){
            alerting(Alert.AlertType.INFORMATION,"Unsuccessfully fetched data","","");
        }
        return regNumbers;
    }

}
