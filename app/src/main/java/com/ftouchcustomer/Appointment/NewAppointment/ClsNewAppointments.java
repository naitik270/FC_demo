package com.ftouchcustomer.Appointment.NewAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsNewAppointments {

    @SerializedName("MerchantId")
    @Expose
    private int MerchantId = 0;

    @SerializedName("MerchantCode")
    @Expose
    private String MerchantCode;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Staffpreference")
    @Expose
    private String Staffpreference;

    @SerializedName("CustomerId")
    @Expose
    private String CustomerId = "";

    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;

    @SerializedName("AppointmentMobileNo")
    @Expose
    private String AppointmentMobileNo;

    @SerializedName("Appointment_Date")
    @Expose
    private String Appointment_Date;

    @SerializedName("AppointmenTime")
    @Expose
    private String AppointmenTime;

    @SerializedName("StatusRemark")
    @Expose
    private String StatusRemark;

    @SerializedName("Services")
    @Expose
    private String Services;


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

    public String getAppointment_Date() {
        return Appointment_Date;
    }

    public void setAppointment_Date(String appointment_Date) {
        Appointment_Date = appointment_Date;
    }

    public String getAppointmenTime() {
        return AppointmenTime;
    }

    public void setAppointmenTime(String appointmenTime) {
        AppointmenTime = appointmenTime;
    }

    public String getStatusRemark() {
        return StatusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        StatusRemark = statusRemark;
    }

    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStaffpreference() {
        return Staffpreference;
    }

    public void setStaffpreference(String staffpreference) {
        Staffpreference = staffpreference;
    }

    public String getAppointmentMobileNo() {
        return AppointmentMobileNo;
    }

    public void setAppointmentMobileNo(String appointmentMobileNo) {
        AppointmentMobileNo = appointmentMobileNo;
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
