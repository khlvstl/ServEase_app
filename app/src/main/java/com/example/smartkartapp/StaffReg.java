package com.example.smartkartapp;

public class StaffReg {
    String staffname,password,id;
    public StaffReg(){

    }

    public StaffReg(String staffname, String password, String id) {
        this.staffname = staffname;
        this.password = password;
        this.id = id;
    }

    public String getStaffname() {
        return staffname;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }
}
