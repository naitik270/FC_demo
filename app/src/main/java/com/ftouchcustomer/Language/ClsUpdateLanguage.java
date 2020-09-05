package com.ftouchcustomer.Language;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsUpdateLanguage {

    @SerializedName("Language")
    @Expose
    private String language;

    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
