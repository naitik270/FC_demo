package com.ftouchcustomer.Address.getAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsGetCustomerAddressListResponse implements Serializable {

    @SerializedName("AddressID")
    @Expose
    private Integer addressID;
    @SerializedName("CustomerMasterID")
    @Expose
    private Integer customerMasterID;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("AddressMobileNo")
    @Expose
    private String AddressMobileNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Default")
    @Expose
    private Boolean _default;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("AddressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("Landmark")
    @Expose
    private String landmark;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("Type")
    @Expose
    private String type;

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getCustomerMasterID() {
        return customerMasterID;
    }

    public void setCustomerMasterID(Integer customerMasterID) {
        this.customerMasterID = customerMasterID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddressMobileNo() {
        return AddressMobileNo;
    }

    public void setAddressMobileNo(String addressMobileNo) {
        AddressMobileNo = addressMobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
