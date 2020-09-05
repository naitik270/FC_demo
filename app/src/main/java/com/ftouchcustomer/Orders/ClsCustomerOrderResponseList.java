package com.ftouchcustomer.Orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsCustomerOrderResponseList {

    public boolean expanded = false;
    public boolean parent = false;

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("OrderNo")
    @Expose
    private String orderNo;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("MerchantID")
    @Expose
    private Integer merchantID;
    @SerializedName("MerchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("AddressType")
    @Expose
    private String addressType;
    @SerializedName("AddressMobileNo")
    @Expose
    private Object addressMobileNo;
    @SerializedName("AddressName")
    @Expose
    private Object addressName;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("DeliveryType")
    @Expose
    private String deliveryType;
    @SerializedName("Items")
    @Expose
    private Integer items;
    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("PaymentDateStr")
    @Expose
    private String paymentDateStr;
    @SerializedName("PaymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("PaymentReferenceNo")
    @Expose
    private String paymentReferenceNo;
    @SerializedName("OrderDateStr")
    @Expose
    private String orderDateStr;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("AddressID")
    @Expose
    private Integer addressID;
    @SerializedName("OrderItemJsonUrl")
    @Expose
    private String orderItemJsonUrl;
    @SerializedName("PaymentFileUrl")
    @Expose
    private Object paymentFileUrl;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("TotalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("GrandTotal")
    @Expose
    private Double grandTotal;

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(Integer merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Object getAddressMobileNo() {
        return addressMobileNo;
    }

    public void setAddressMobileNo(Object addressMobileNo) {
        this.addressMobileNo = addressMobileNo;
    }

    public Object getAddressName() {
        return addressName;
    }

    public void setAddressName(Object addressName) {
        this.addressName = addressName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDateStr() {
        return paymentDateStr;
    }

    public void setPaymentDateStr(String paymentDateStr) {
        this.paymentDateStr = paymentDateStr;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentReferenceNo() {
        return paymentReferenceNo;
    }

    public void setPaymentReferenceNo(String paymentReferenceNo) {
        this.paymentReferenceNo = paymentReferenceNo;
    }

    public String getOrderDateStr() {
        return orderDateStr;
    }

    public void setOrderDateStr(String orderDateStr) {
        this.orderDateStr = orderDateStr;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public String getOrderItemJsonUrl() {
        return orderItemJsonUrl;
    }

    public void setOrderItemJsonUrl(String orderItemJsonUrl) {
        this.orderItemJsonUrl = orderItemJsonUrl;
    }

    public Object getPaymentFileUrl() {
        return paymentFileUrl;
    }

    public void setPaymentFileUrl(Object paymentFileUrl) {
        this.paymentFileUrl = paymentFileUrl;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

}
