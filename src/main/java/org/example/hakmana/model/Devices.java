package org.example.hakmana.model;

public abstract class Devices {
    private String regNum;
    private String model="No";
    private String userName="No User";
    private String status;

    public Devices(String regNum, String model, String userName,String status) {
        this.regNum = regNum;
        this.model = model;
        this.userName = userName;
        this.status=status;
    }

    public Devices() {
    }

    //get the Devices array from the database
    public Devices[] getDevices(){
        return null;
    }

    public abstract void setRegNum(String para1);
    public abstract String getRegNum();

    public abstract void setModel(String para1);
    public abstract String getModel();

    public abstract String getUserName();
    public abstract void setUserName(String para1);

    public abstract void setStatus(String para1);
    public abstract String getStatus();

}


