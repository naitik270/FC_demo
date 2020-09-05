package com.ftouchcustomer.Appointment.GetAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsGetAppointment {

    @SerializedName("CurrentIndex")
    @Expose
    private int CurrentIndex;

    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;

    @SerializedName("Mode")
    @Expose
    private String Mode;

    @SerializedName("MerchantCode")
    @Expose
    private String MerchantCode;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("FromDate")
    @Expose
    private String FromDate;

    @SerializedName("ToDate")
    @Expose
    private String ToDate;


    public int getCurrentIndex() {
        return CurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        CurrentIndex = currentIndex;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }
}
