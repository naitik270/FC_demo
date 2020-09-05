package com.ftouchcustomer.OTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsSendOtp {

    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("UserType")
    @Expose
    private String UserType;
    @SerializedName("OTPSendMode")
    @Expose
    private String OtpSendMode;

    public String getOtpSendMode() {
        return OtpSendMode;
    }

    public void setOtpSendMode(String otpSendMode) {
        OtpSendMode = otpSendMode;
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

}
