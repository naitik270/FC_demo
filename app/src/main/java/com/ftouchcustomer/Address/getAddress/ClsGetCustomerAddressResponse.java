package com.ftouchcustomer.Address.getAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsGetCustomerAddressResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ClsGetCustomerAddressListResponse> data = null;

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

    public List<ClsGetCustomerAddressListResponse> getData() {
        return data;
    }

    public void setData(List<ClsGetCustomerAddressListResponse> data) {
        this.data = data;
    }
}
