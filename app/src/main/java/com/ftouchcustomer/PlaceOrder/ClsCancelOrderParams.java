package com.ftouchcustomer.PlaceOrder;

public class ClsCancelOrderParams {


    int OrderID = 0;
    String MobileNo = "";
    String MerchantCode = "";
    String CancelledBy = "";
    String Mode = "";
    String Reason = "";

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public String getCancelledBy() {
        return CancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        CancelledBy = cancelledBy;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
