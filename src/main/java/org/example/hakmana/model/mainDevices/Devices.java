package org.example.hakmana.model.mainDevices;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public abstract class Devices {
    private static final Logger sqlLogger= (Logger) LogManager.getLogger(Devices.class);
    private final DatabaseConnection conn=DatabaseConnection.getInstance();
    private String regNum;
    private String model="No";
    private String name;
    private String status;
    public Devices(String regNum, String model, String name, String status) {
        this.regNum = regNum;
        this.model = model;
        this.name = name;
        this.status = status;
    }
    public Devices() {
    }

    //get the Devices array from the database
    public  Devices[] getDevices(){
        return null;
    }

    public Devices getDevice(String regNum){return null;}

    public abstract void setRegNum(String para1);
    public abstract String getRegNum();

    public abstract void setModel(String para1);
    public abstract String getModel();

    public abstract String getUserName();
    public abstract void setUserName(String para1);

    public abstract void setStatus(String para1);
    public abstract String getStatus();

    public Optional<ButtonType> alerting(Alert.AlertType alertType, String title, String header, String content){
        Alert alert=new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    private void changesCommit(Connection conn, Optional<ButtonType> alertResult) throws SQLException {
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            conn.commit();
            return;
        }
        conn.rollback();
    }

    public Boolean dbInteraction(String incompleteSql,ArrayList<String> valList,String deviceRegNum){
        Connection connection= conn.getConnection();
        try {
            connection.setAutoCommit(false);
            int i=1;
            PreparedStatement ps = connection.prepareStatement(incompleteSql);
            for(String l:valList){
                ps.setString(i,l);
                i++;
            }
            i= ps.executeUpdate();

            changesCommit(connection,alerting(Alert.AlertType.CONFIRMATION,"Confirmation","registration number: "+deviceRegNum,"Updated "+ i+" rows " ));
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            // Rollback the transaction on error
            sqlLogger.error(e.getMessage());
            alerting(Alert.AlertType.WARNING,"Warning","Something went wrong",e.getMessage());
            return false;
        }
    }

    public ArrayList<String> regNumbGetQueryExecute(String qry,String regNumcolumnName){
        ArrayList<String> regNumbers=new ArrayList<>();
        try(ResultSet rs = conn.executeSt(qry)){
            while(rs.next()){
                regNumbers.add(rs.getString(regNumcolumnName));
            }
        }catch (SQLException e){
          alerting(Alert.AlertType.INFORMATION,"Unsuccessfully fetched data","","");
        }
        return regNumbers;
    }

    public void deleteDevice(String devRegNum,String RegNumType,String table)  {
       try {
           Statement str=conn.getConnection().createStatement();
           str.executeUpdate("DELETE FROM "+table+" WHERE "+RegNumType+"='"+devRegNum+"'");
           successAlert();
       }
       catch(SQLException e){
                failAlert();
        }
    }
    public void successAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null); // Header text can be null
        alert.setContentText("Device removed successfully!");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }
    public void failAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null); // Header text can be null
        alert.setContentText("Cannot remove: device is already assigned to another device.!");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }
}


