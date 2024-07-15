package org.example.hakmana.model.otherDevices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.example.hakmana.model.DatabaseConnection;
import org.example.hakmana.model.mainDevices.Desktop;
import org.example.hakmana.model.mainDevices.Devices;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OtherDevices extends Devices {
    private static final Logger sqlLogger= (Logger) LogManager.getLogger(OtherDevices.class);
    private DatabaseConnection databaseConnection ;
    private final DatabaseConnection conn=DatabaseConnection.getInstance();
    private static OtherDevices otherDevicesInstance=null;
    private static Connection connection;
    private static List<String> excludedTables;
    private static List<String> devices= new ArrayList<>();
    private static boolean devicesLoaded = false;

    //table column values
    private int num;
    private String dev;
    private int totalDev;
    private int numActiveDev;
    private int numInactiveDev;
    private int numRepairingDev;
    private ObservableList<OtherDevices> observableOtherDevices = FXCollections.observableArrayList();
    private boolean tblRowLoaded=false;

    private String RegNum;
    private String Model;
    private String UserName;
    private String Status;

    /*-----------------constructors for this class---------------*/
    public OtherDevices() {

    }

    public static OtherDevices getOtherDevicesInstance() {
        if(otherDevicesInstance==null){
            otherDevicesInstance=new OtherDevices();
            return otherDevicesInstance;
        }
        return otherDevicesInstance;
    }

    public OtherDevices[] getSpDevices(String dbSelector) {
        List<OtherDevices> otherDevices = new ArrayList<>();
        //pass query to the connection class
        String sql = "SELECT "+dbSelector+"."+dbSelector+"RegNum,"+dbSelector+".model,"+dbSelector+".status FROM "+dbSelector;

        try (ResultSet resultSet = conn.executeSt(sql)) {// get result set from connection class and auto closable

            // Iterate through the result set and create Desktop and DeviceUser objects
            while (resultSet.next()) {
                OtherDevices otherDevice = new OtherDevices();
                otherDevice.setRegNum(resultSet.getString(dbSelector+"RegNum"));
                otherDevice.setModel(resultSet.getString("model"));
                otherDevice.setStatus(resultSet.getString("status"));
                otherDevice.setUserName("not dev yet"); //not dev yet

                otherDevices.add(otherDevice);//add desktop to the desktops list
            }
        } catch (SQLException e) {
            alerting(Alert.AlertType.WARNING,"Error Updating Device","An error occurred while updating the device.",e.getMessage());
        }

        //return desktops list as an array
        return otherDevices.toArray(new OtherDevices[0]);
    }

    @Override
    public void setRegNum(String RegNum) {
        this.RegNum=RegNum;
    }

    @Override
    public String getRegNum() {
        return this.RegNum;
    }

    @Override
    public void setModel(String Model) {
        this.Model=Model;
    }

    @Override
    public String getModel() {
        return this.Model;
    }

    @Override
    public String getUserName() {
        return UserName;
    }

    @Override
    public void setUserName(String UserName) {
        this.UserName=UserName;
    }

    @Override
    public void setStatus(String Status) {
        this.Status=Status;
    }

    @Override
    public String getStatus() {
        return this.Status;
    }

    //This constructor Especially for the table data  inserting purpose
    public OtherDevices(int num, String dev, int totalDev, int numActiveDev, int numInactiveDev, int numRepairingDev) {
        this.num = num;
        this.dev = dev;
        this.totalDev = totalDev;
        this.numActiveDev = numActiveDev;
        this.numInactiveDev = numInactiveDev;
        this.numRepairingDev = numRepairingDev;
    }

    /*---------------------getters and seetters--------------------*/
    public static List<String> getExcludedTables() {
        return excludedTables;
    }
    public List<String> getDevicesList() {
        //create the database connection
        databaseConnection = DatabaseConnection.getInstance();
        connection = databaseConnection.getConnection();
        //this list contains the main devices list
        excludedTables = Arrays.asList("desktop","monitors","laptop","printer","ups","multimediaprojector","photocopymachine","systemuser","deviceuser","notes");

        if (!devicesLoaded) {
            // Fetch table names and populate devices list
            fetchTableNames();
            devicesLoaded = true;
        }
        return devices;
    }

    //These getters are especially for the table data inserting
    public int getNumActiveDev() {
        return numActiveDev;
    }
    public int getNumInactiveDev() {
        return numInactiveDev;
    }
    public int getNumRepairingDev() {
        return numRepairingDev;
    }
    public int getNum() {
        return num;
    }
    public String getDev() {
        return dev;
    }
    public int getTotalDev() {
        return totalDev;
    }
    public boolean isTblRowLoaded() {
        return tblRowLoaded;
    }
    public ObservableList<OtherDevices> getObservableOtherDevices() {
        if (!isTblRowLoaded()) {
            setOtherDeviceTblDetails();
            tblRowLoaded=true;
        }
        return observableOtherDevices;
    }

    //This method fetch the other devices list from the database
    private static void fetchTableNames(){
        String sql = "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'hakmanaedm'AND TABLE_NAME NOT IN(";
        for (int i = 0; i < getExcludedTables().size(); i++) {
            sql += "'" + getExcludedTables().get(i) + "'";
            if (i != getExcludedTables().size() - 1) {
                sql += ",";
            }
        }
        sql += ");";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                devices.add(resultSet.getString("TABLE_NAME"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            sqlLogger.error("An sql error occur",e);
            throw new RuntimeException(e);
        }
    }

    //This method set the rows of the table and add to the Observable list
    private void setOtherDeviceTblDetails() {
            int row = 1;// Start adding devices from row 1 (after header row)
            for (String d : getDevicesList()) {
                int numActiveDev = 0;
                int numInactiveDev = 0;
                int numRepairingDev = 0;

                try {
                    // SQL query without placeholders
                    String sqlActive = "SELECT COUNT(*) AS num_active_dev FROM " + d + " WHERE status = 'active';";
                    String sqlInactive = "SELECT COUNT(*) AS num_inactive_dev FROM " + d + " WHERE status = 'inactive';";
                    String sqlRepairing = "SELECT COUNT(*) AS num_repairing_dev FROM " + d + " WHERE status = 'repairing';";

                    // Execute queries for each status
                    ResultSet resultSet;

                    // Active devices
                    PreparedStatement preparedStatementActive = connection.prepareStatement(sqlActive);
                    resultSet = preparedStatementActive.executeQuery();
                    if (resultSet.next()) {
                        numActiveDev = resultSet.getInt("num_active_dev");
                    }
                    resultSet.close();
                    preparedStatementActive.close();

                    // Inactive devices
                    PreparedStatement preparedStatementInactive = connection.prepareStatement(sqlInactive);
                    resultSet = preparedStatementInactive.executeQuery();
                    if (resultSet.next()) {
                        numInactiveDev = resultSet.getInt("num_inactive_dev");
                    }
                    resultSet.close();
                    preparedStatementInactive.close();

                    // Repairing devices
                    PreparedStatement preparedStatementRepairing = connection.prepareStatement(sqlRepairing);
                    resultSet = preparedStatementRepairing.executeQuery();
                    if (resultSet.next()) {
                        numRepairingDev = resultSet.getInt("num_repairing_dev");
                    }
                    resultSet.close();
                    preparedStatementRepairing.close();
                } catch (SQLException e) {
                    sqlLogger.error("An sql error occur",e);
                    e.getMessage();
                }

                // Print results or use them as needed
                //System.out.println(numActiveDev+ "\t"+ numInactiveDev+ "\t"+ numRepairingDev);
                observableOtherDevices.add(new OtherDevices(row, d,
                        numActiveDev+numInactiveDev+numRepairingDev,
                        numActiveDev, numInactiveDev, numRepairingDev));
                row++;
            }

    }

    public boolean createNewDevCat(String newDev) {
        String sql = "CREATE TABLE " + newDev + " (" +
                newDev + "RegNum VARCHAR(13) PRIMARY KEY NOT NULL, " +
                "model VARCHAR(25), " +
                "purchasedFrom VARCHAR(50), " +
                "status VARCHAR(10));";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            return false;
        }
    }

}

