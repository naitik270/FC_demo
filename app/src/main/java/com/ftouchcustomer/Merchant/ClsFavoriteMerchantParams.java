package com.ftouchcustomer.Merchant;

public class ClsFavoriteMerchantParams {

    String CustomerMobileNo = "";
    String MerchantCode = "";
    String Favourite = "";
    int CustomerID = 0;

    public String getCustomerMobileNo() {
        return CustomerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        CustomerMobileNo = customerMobileNo;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public String getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }
}
