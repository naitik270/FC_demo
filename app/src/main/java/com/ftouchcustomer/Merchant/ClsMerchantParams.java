package com.ftouchcustomer.Merchant;

public class ClsMerchantParams {

    private int CurrentIndex = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String CompanyName = "";
    private String ProductName = "";
    private String PinCode = "";
    private String Address = "";
    private String MobileNo = "";
    private String Category = "";
    private String State = "";
    private String City = "";
    private String CustomerMobileNo = "";
    private String FavouriteMerchant = "";

    public String getOnlineStore() {
        return OnlineStore;
    }

    public void setOnlineStore(String onlineStore) {
        OnlineStore = onlineStore;
    }

    private String OnlineStore = "";

    public String getCustomerMobileNo() {
        return CustomerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        CustomerMobileNo = customerMobileNo;
    }

    public String getFavouriteMerchant() {
        return FavouriteMerchant;
    }

    public void setFavouriteMerchant(String favouriteMerchant) {
        FavouriteMerchant = favouriteMerchant;
    }

    public int getCurrentIndex() {
        return CurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        CurrentIndex = currentIndex;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
}
