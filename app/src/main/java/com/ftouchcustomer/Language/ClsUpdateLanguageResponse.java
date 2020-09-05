package com.ftouchcustomer.Language;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsUpdateLanguageResponse {

    @SerializedName("success")
    @Expose
    private String sUCCESS;
    @SerializedName("message")
    @Expose
    private String mESSAGE;

    public String getSUCCESS() {
        return sUCCESS;
    }

    public void setSUCCESS(String sUCCESS) {
        this.sUCCESS = sUCCESS;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }
}
