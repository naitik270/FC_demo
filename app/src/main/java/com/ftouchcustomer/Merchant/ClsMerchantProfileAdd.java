package com.ftouchcustomer.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsMerchantProfileAdd {

    @SerializedName("MerchantCode")
    @Expose
    private String MerchantCode;
    @SerializedName("CustomerId")
    @Expose
    private String CustomerId;
    @SerializedName("CustomerMobileNo")
    @Expose
    private String CustomerMobileNo;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("AskingFor")
    @Expose
    private String AskingFor;
    @SerializedName("Comment")
    @Expose
    private String Comment;

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
        return CustomerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        CustomerMobileNo = customerMobileNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAskingFor() {
        return AskingFor;
    }

    public void setAskingFor(String askingFor) {
        AskingFor = askingFor;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
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
