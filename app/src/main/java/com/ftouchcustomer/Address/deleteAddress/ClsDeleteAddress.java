package com.ftouchcustomer.Address.deleteAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsDeleteAddress {

    @SerializedName("MobileNo")
    @Expose
    private String MobileNo = "";

    @SerializedName("AddressID")
    @Expose
    private int AddressID;

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
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
