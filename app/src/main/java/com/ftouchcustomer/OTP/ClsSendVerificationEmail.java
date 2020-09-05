package com.ftouchcustomer.OTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsSendVerificationEmail {

    @SerializedName("CustomerCode")
    @Expose
    private String CustomerCode;
    @SerializedName("EmailToVerify")
    @Expose
    private String EmailToVerify;
    @SerializedName("AppName")
    @Expose
    private String AppName;
    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("UserType")
    @Expose
    private String UserType;

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getEmailToVerify() {
        return EmailToVerify;
    }

    public void setEmailToVerify(String emailToVerify) {
        EmailToVerify = emailToVerify;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }


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
