package com.ftouchcustomer.Global;

public class ClsUserInfo {

    private String CustomerId = "0";
    private String Name = "";
    private String LoginStatus = "";
    private String RegisteredMobileNumber = "";
    private String EmailAddress = "";
    private String state = "";
    private int stateId = 0;
    private String city = "";
    private String pincode = "";
    private String dob = "";
    private String gender = "";

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLoginStatus() {
        return LoginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        LoginStatus = loginStatus;
    }

    public String getRegisteredmobilenumber() {
        return RegisteredMobileNumber;
    }

    public void setRegisteredmobilenumber(String registeredmobilenumber) {
        this.RegisteredMobileNumber = registeredmobilenumber;
    }

    public String getEmailaddress() {
        return EmailAddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.EmailAddress = emailaddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
