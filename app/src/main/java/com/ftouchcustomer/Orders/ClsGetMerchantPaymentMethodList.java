package com.ftouchcustomer.Orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsGetMerchantPaymentMethodList {

    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("Description")
    @Expose
    private String description;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
