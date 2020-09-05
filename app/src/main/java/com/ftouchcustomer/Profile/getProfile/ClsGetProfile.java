package com.ftouchcustomer.Profile.getProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsGetProfile {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("CustomerMasterID")
    @Expose
    private String CustomerMasterID;
    @SerializedName("MobileVerified")
    @Expose
    private String mobileVerified;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("EmailVerified")
    @Expose
    private String emailVerified;
    @SerializedName("StateID")
    @Expose
    private Integer stateID;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(String mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
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

    public String getCustomerMasterID() {
        return CustomerMasterID;
    }

    public void setCustomerMasterID(String customerMasterID) {
        CustomerMasterID = customerMasterID;
    }
}
