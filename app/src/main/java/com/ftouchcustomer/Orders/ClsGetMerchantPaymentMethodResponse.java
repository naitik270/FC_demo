package com.ftouchcustomer.Orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsGetMerchantPaymentMethodResponse {



    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ClsGetMerchantPaymentMethodList> data = null;
    @SerializedName("DeliveryCharges")
    @Expose
    private String deliveryCharges;
    @SerializedName("DeliveryChargesDescription")
    @Expose
    private String deliveryChargesDescription;


    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getDeliveryChargesDescription() {
        return deliveryChargesDescription;
    }

    public void setDeliveryChargesDescription(String deliveryChargesDescription) {
        this.deliveryChargesDescription = deliveryChargesDescription;
    }


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

    public List<ClsGetMerchantPaymentMethodList> getData() {
        return data;
    }

    public void setData(List<ClsGetMerchantPaymentMethodList> data) {
        this.data = data;
    }


}
