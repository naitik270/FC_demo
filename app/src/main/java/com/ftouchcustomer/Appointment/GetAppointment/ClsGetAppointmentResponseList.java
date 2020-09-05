package com.ftouchcustomer.Appointment.GetAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsGetAppointmentResponseList {

    @SerializedName("AppointmentID")
    @Expose
    private Integer appointmentID;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;
    @SerializedName("AppointmentMobileNo")
    @Expose
    private String appointmentMobileNo;
    @SerializedName("AppointmentNo")
    @Expose
    private String appointmentNo;
    @SerializedName("EntryDate")
    @Expose
    private String entryDate;
    @SerializedName("MerchantName")
    @Expose
    private String merchantName;
    @SerializedName("AppointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("Appointment_Request_Date")
    @Expose
    private String appointmentRequestDate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Status_Date")
    @Expose
    private String statusDate;
    @SerializedName("StatusRemark")
    @Expose
    private String statusRemark;
    @SerializedName("Services")
    @Expose
    private String services;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("CancelledBy")
    @Expose
    private String cancelledBy;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public String getAppointmentMobileNo() {
        return appointmentMobileNo;
    }

    public void setAppointmentMobileNo(String appointmentMobileNo) {
        this.appointmentMobileNo = appointmentMobileNo;
    }

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentRequestDate() {
        return appointmentRequestDate;
    }

    public void setAppointmentRequestDate(String appointmentRequestDate) {
        this.appointmentRequestDate = appointmentRequestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
