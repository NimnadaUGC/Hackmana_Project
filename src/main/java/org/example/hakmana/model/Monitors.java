package org.example.hakmana.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Monitors extends Devices{
    private DatabaseConnection conn;
    private String regNum;
    private String model;
    private String status;
    private String userName;
    private String screenSize;
    private String userNIC = "No User";
    private String regNumDesktop="no desktop";

    public Monitors(String regNum, String model, String userName, String status, String screenSize, String userNIC, String regNumDesktop) {
        super(regNum, model, userName, status);
        this.screenSize = screenSize;
        this.userNIC = userNIC;
        this.regNumDesktop = regNumDesktop;
    }

    public Monitors(String regNum, String model, String userName, String status) {
        super(regNum, model, userName,status);
    }

    public Monitors() {
    }

    @Override
    public String getRegNum() {
        return regNum;
    }
    @Override
    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }
    @Override
    public String getModel() {
        return model;
    }
    @Override
    public void setModel(String model) {
        this.model = model;
    }
    @Override
    public String getStatus() {
        return status;
    }
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String getUserName() {
        return userName;
    }
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScreenSize() {
        return screenSize;
    }
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }
    public String getUserNIC() {
        return userNIC;
    }
    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }
    public String getRegNumDesktop() {
        return regNumDesktop;
    }
    public void setRegNumDesktop(String regNumDesktop) {
        this.regNumDesktop = regNumDesktop;
    }

    public Monitors[] getDevices() {
        conn=DatabaseConnection.getInstance();
        List<Monitors> monitors = new ArrayList<>();
        //pass query to the connection class
        String sql = "SELECT monitors.regNum,monitors.model,monitors.status, user.name FROM monitors LEFT JOIN user ON monitors.userNIC = user.userNIC";

        try {
            // get result set from connection class
            ResultSet resultSet = conn.executeSt(sql);

            // Iterate through the result set and create Desktop and User objects
            while (resultSet.next()) {
                Monitors monitor = new Monitors(null,null,null,null);

                monitor.setRegNum(resultSet.getString("regNum"));
                monitor.setModel(resultSet.getString("model"));
                monitor.setStatus(resultSet.getString("status"));
                monitor.setUserName(resultSet.getString("name"));


                monitors.add(monitor);//add monitor to the monitors list
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

        return monitors.toArray(new Monitors[0]);
    }
    @Override
    public Monitors getDevice(String regNum) {
        conn = DatabaseConnection.getInstance();
        //pass query to the connection class
        String sql = "SELECT * FROM monitors Where regNum=?";

        try {
            PreparedStatement ps = conn.getConnection().prepareStatement(sql);
            ps.setString(1, regNum);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Monitors monitors = new Monitors();
                monitors.setRegNum(rs.getString("regNum"));
                monitors.setModel(rs.getString("model"));
                monitors.setStatus(rs.getString("status"));
                monitors.setUserNIC(rs.getString("userNIC"));
                monitors.setRegNumDesktop(rs.getString("regNumDesktop"));

                return monitors;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //return null if there is no result
        return null;
    }
    public boolean updateDevice(ArrayList<String> list){
        conn = DatabaseConnection.getInstance();
        Connection connection= conn.getConnection();
        //pass query to the connection class
        String sql="UPDATE monitors SET model=?,status=?,regNumDesktop=? WHERE regNUM=?";
        try {
            connection.setAutoCommit(false);

            int i=1;
            PreparedStatement ps = connection.prepareStatement(sql);
            for(String l:list){
                ps.setString(i,l);
                i++;
            }

            i=ps.executeUpdate();

            //Check confirmation to change
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Update "+ i+" rows desktop registration number " +list.get(3));

            Optional<ButtonType> alertResult = alert.showAndWait();//wait until button press in alert box

            //if alert box ok pressed execute sql quires
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                // commit the sql quires
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

        } catch (SQLException e) {
            // Rollback the transaction on error
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Updating Device");
            alert.setHeaderText("An error occurred while updating the device.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }
    public boolean insertDevice(ArrayList<String> list){
        conn = DatabaseConnection.getInstance();
        Connection connection= conn.getConnection();
        //pass query to the connection class
        String sql="INSERT INTO monnitors (regNum,model,status,regNumDesktop)" +
                "VALUES (?,?,?,?))";
        try {
            connection.setAutoCommit(false);

            int i=1;
            PreparedStatement ps = connection.prepareStatement(sql);
            for(String l:list){
                ps.setString(i,l);
                i++;
            }

            i=ps.executeUpdate();

            //Check confirmation to change
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Update "+ i+" rows desktop registration number " +list.get(20));

            Optional<ButtonType> alertResult = alert.showAndWait();//wait until button press in alert box

            //if alert box ok pressed execute sql quires
            if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                // commit the sql quires
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

        } catch (SQLException e) {
            // Rollback the transaction on error
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Updating Device");
            alert.setHeaderText("An error occurred while updating the device.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }
}
