package com.ftouchcustomer.OTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsVerifyOtp {

    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("VerificationCode")
    @Expose
    private String VerificationCode;
    @SerializedName("UserType")
    @Expose
    private String UserType;
    @SerializedName("OTPSendMode")
    @Expose
    private String OTPSendMode;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        VerificationCode = verificationCode;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getOTPSendMode() {
        return OTPSendMode;
    }

    public void setOTPSendMode(String OTPSendMode) {
        this.OTPSendMode = OTPSendMode;
    }


    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
