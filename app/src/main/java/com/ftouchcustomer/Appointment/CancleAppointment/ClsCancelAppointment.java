package com.ftouchcustomer.Appointment.CancleAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsCancelAppointment {

    @SerializedName("MerchantId")
    @Expose
    private int MerchantId;

    @SerializedName("MerchantCode")
    @Expose
    private String MerchantCode;

    @SerializedName("AppointmentId")
    @Expose
    private int AppointmentId;

    @SerializedName("CustomerId")
    @Expose
    private String CustomerId;

    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;

    @SerializedName("Reason")
    @Expose
    private String Reason;

    @SerializedName("CancelledBy")
    @Expose
    private String CancelledBy;

    @SerializedName("Mode")
    @Expose
    private String Mode;


    public int getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(int merchantId) {
        MerchantId = merchantId;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public int getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        AppointmentId = appointmentId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
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
