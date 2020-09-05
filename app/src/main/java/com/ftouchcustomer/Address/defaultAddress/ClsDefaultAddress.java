package com.ftouchcustomer.Address.defaultAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsDefaultAddress {

    @SerializedName("MobileNo")
    @Expose
    private String mobile;

    @SerializedName("Default")
    @Expose
    private boolean defaultAddress;

    @SerializedName("AddressID")
    @Expose
    private int addressId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
