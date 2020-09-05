package com.ftouchcustomer.Payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsCustomerPaymentParams {

  /*  int OrderID = 0;
    int CustomerID = 0;
    int MerchantID = 0;
    String CustomerMobileNo = "";
    String MerchantCode = "";
    String PaymentReferenceNo = "";
    String PaymentStatus = "";
    String UploadFile = "";

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(int merchantID) {
        MerchantID = merchantID;
    }

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

    public String getPaymentReferenceNo() {
        return PaymentReferenceNo;
    }

    public void setPaymentReferenceNo(String paymentReferenceNo) {
        PaymentReferenceNo = paymentReferenceNo;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getUploadFile() {
        return UploadFile;
    }

    public void setUploadFile(String uploadFile) {
        UploadFile = uploadFile;
    }*/

    @SerializedName("success")
    @Expose
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
