package com.ftouchcustomer.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ClsMerchantResponseList implements Serializable {

    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("BusinessName")
    @Expose
    private String businessName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CapturedAddress")
    @Expose
    private String capturedAddress;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("Categories")
    @Expose
    private String categories;
    @SerializedName("DisplayCategories")
    @Expose
    private String displayCategories;
    @SerializedName("Distance")
    @Expose
    private String distance;
    @SerializedName("LogoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("MenuUrl")
    @Expose
    private String menuUrl;
    @SerializedName("MenuImgsUrl")
    @Expose
    private String menuImgsUrl;
    @SerializedName("ServiceUrl")
    @Expose
    private String serviceUrl;
    @SerializedName("Favourite")
    @Expose
    private String Favourite;


    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("OnlineSellingStatus")
    @Expose
    private String onlineSellingStatus;


    @SerializedName("DeliveryCharges")
    @Expose
    private String deliveryCharges;
    @SerializedName("DeliveryChargesDescription")
    @Expose
    private String deliveryChargesDescription;


    @SerializedName("Timing")
    @Expose
    private List<ClsTiming> timing = null;

    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCapturedAddress() {
        return capturedAddress;
    }

    public void setCapturedAddress(String capturedAddress) {
        this.capturedAddress = capturedAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDisplayCategories() {
        return displayCategories;
    }

    public void setDisplayCategories(String displayCategories) {
        this.displayCategories = displayCategories;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuImgsUrl() {
        return menuImgsUrl;
    }

    public void setMenuImgsUrl(String menuImgsUrl) {
        this.menuImgsUrl = menuImgsUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getOnlineSellingStatus() {
        return onlineSellingStatus;
    }

    public void setOnlineSellingStatus(String onlineSellingStatus) {
        this.onlineSellingStatus = onlineSellingStatus;
    }


    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getDeliveryChargesDescription() {
        return deliveryChargesDescription;
    }

    public void setDeliveryChargesDescription(String deliveryChargesDescription) {
        this.deliveryChargesDescription = deliveryChargesDescription;
    }

    public List<ClsTiming> getTiming() {
        return timing;
    }

    public void setTiming(List<ClsTiming> timing) {
        this.timing = timing;
    }


    public boolean isFavorite;

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}