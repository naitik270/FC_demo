package com.ftouchcustomer.Profile.updateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsUpdateProfile {

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("MobileNo")
    @Expose
    private String mobileNumber;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("StateID")
    @Expose
    private int stateId;

    @SerializedName("State")
    @Expose
    private String state;

    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("PinCode")
    @Expose
    private String pinCode;

    @SerializedName("DOB")
    @Expose
    private String DOB;

    @SerializedName("Gender")
    @Expose
    private String Gender;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
