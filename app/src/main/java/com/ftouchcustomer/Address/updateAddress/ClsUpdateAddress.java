package com.ftouchcustomer.Address.updateAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsUpdateAddress {

    @SerializedName("MobileNo")
    @Expose
    private String MobileNo = "";

    @SerializedName("AddressMobileNo")
    @Expose
    private String AddressMobileNo = "";

    @SerializedName("Name")
    @Expose
    private String Name = "";

    @SerializedName("Default")
    @Expose
    private boolean Default;

    @SerializedName("AddressLine1")
    @Expose
    private String AddressLine1 = "";

    @SerializedName("AddressLine2")
    @Expose
    private String AddressLine2 = "";

    @SerializedName("Landmark")
    @Expose
    private String Landmark = "";

    @SerializedName("StateID")
    @Expose
    private int StateID;

    @SerializedName("State")
    @Expose
    private String State = "";

    @SerializedName("City")
    @Expose
    private String City = "";

    @SerializedName("PinCode")
    @Expose
    private String PinCode;

    @SerializedName("Type")
    @Expose
    private String Type = "";

    @SerializedName("AddressID")
    @Expose
    private int AddressID;


    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getAddressMobileNo() {
        return AddressMobileNo;
    }

    public void setAddressMobileNo(String addressMobileNo) {
        AddressMobileNo = addressMobileNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isDefault() {
        return Default;
    }

    public void setDefault(boolean aDefault) {
        Default = aDefault;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int stateID) {
        StateID = stateID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int addressID) {
        AddressID = addressID;
    }

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
