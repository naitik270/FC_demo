package com.ftouchcustomer.Orders;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsCustomerOrderDetail {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ClsDataOrderDetails data;

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

    public ClsDataOrderDetails getData() {
        return data;
    }

    public void setData(ClsDataOrderDetails data) {
        this.data = data;
    }

    public static class ClsDataOrderDetails implements Serializable {


        @SerializedName("OrderNO")
        @Expose
        private String orderNO;
        @SerializedName("OrderID")
        @Expose
        private Integer orderID;
        @SerializedName("CustomerID")
        @Expose
        private Integer customerID;
        @SerializedName("MerchantID")
        @Expose
        private Integer merchantID;
        @SerializedName("MerchantCode")
        @Expose
        private String merchantCode;
        @SerializedName("MerchantName")
        @Expose
        private String merchantName;
        @SerializedName("DeliveryType")
        @Expose
        private String deliveryType;
        @SerializedName("Itemscount")
        @Expose
        private Integer itemscount;
        @SerializedName("PaymentMethod")
        @Expose
        private String paymentMethod;
        @SerializedName("PaymentDate")
        @Expose
        private String paymentDate;
        @SerializedName("PaymentStatus")
        @Expose
        private String paymentStatus;
        @SerializedName("PaymentReferenceNo")
        @Expose
        private String paymentReferenceNo;
        @SerializedName("ItemsFileUrl")
        @Expose
        private String itemsFileUrl;
        @SerializedName("OrderStatus")
        @Expose
        private String orderStatus;
        @SerializedName("CancelReason")
        @Expose
        private String cancelReason;
        @SerializedName("CancelledBy")
        @Expose
        private String cancelledBy;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("InvoiceURL")
        @Expose
        private String invoiceURL;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("OrderPlaceDate")
        @Expose
        private String orderPlaceDate;
        @SerializedName("DeliveredDate")
        @Expose
        private String deliveredDate;
        @SerializedName("Readydate")
        @Expose
        private String readydate;
        @SerializedName("Preparingdate")
        @Expose
        private String preparingdate;
        @SerializedName("Confirmeddate")
        @Expose
        private String confirmeddate;
        @SerializedName("Completedate")
        @Expose
        private String completedate;
        @SerializedName("Cancelleddate")
        @Expose
        private String cancelleddate;
        @SerializedName("PaymentFileUrl")
        @Expose
        private String paymentFileUrl;
        @SerializedName("ReceivedBy")
        @Expose
        private String receivedBy;
        @SerializedName("ReceivedMobileNO")
        @Expose
        private String receivedMobileNO;
        @SerializedName("DeliveryComment")
        @Expose
        private String deliveryComment;
        @SerializedName("ConfirmBy")
        @Expose
        private String confirmBy;
        @SerializedName("DeliveryBy")
        @Expose
        private String deliveryBy;
        @SerializedName("AddressType")
        @Expose
        private String addressType;
        @SerializedName("DeliveryCharges")
        @Expose
        private Double deliveryCharges;
        @SerializedName("TotalAmount")
        @Expose
        private Double totalAmount;
        @SerializedName("GrandTotal")
        @Expose
        private Double grandTotal;
        @SerializedName("ContactPersonMobileNo")
        @Expose
        private String contactPersonMobileNo;
        @SerializedName("ContactPerson")
        @Expose
        private String contactPerson;
        @SerializedName("MobileNo")
        @Expose
        private String mobileNo;
        @SerializedName("Contact_address")
        @Expose
        private String contactAddress;
        @SerializedName("AddressID")
        @Expose
        private Integer addressID;
        @SerializedName("ContactEmail")
        @Expose
        private String contactEmail;

        public String getOrderNO() {
            return orderNO;
        }

        public void setOrderNO(String orderNO) {
            this.orderNO = orderNO;
        }

        public Integer getOrderID() {
            return orderID;
        }

        public void setOrderID(Integer orderID) {
            this.orderID = orderID;
        }

        public Integer getCustomerID() {
            return customerID;
        }

        public void setCustomerID(Integer customerID) {
            this.customerID = customerID;
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

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public Integer getItemscount() {
            return itemscount;
        }

        public void setItemscount(Integer itemscount) {
            this.itemscount = itemscount;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
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

        public String getItemsFileUrl() {
            return itemsFileUrl;
        }

        public void setItemsFileUrl(String itemsFileUrl) {
            this.itemsFileUrl = itemsFileUrl;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
            this.cancelReason = cancelReason;
        }

        public String getCancelledBy() {
            return cancelledBy;
        }

        public void setCancelledBy(String cancelledBy) {
            this.cancelledBy = cancelledBy;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getInvoiceURL() {
            return invoiceURL;
        }

        public void setInvoiceURL(String invoiceURL) {
            this.invoiceURL = invoiceURL;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getOrderPlaceDate() {
            return orderPlaceDate;
        }

        public void setOrderPlaceDate(String orderPlaceDate) {
            this.orderPlaceDate = orderPlaceDate;
        }

        public String getDeliveredDate() {
            return deliveredDate;
        }

        public void setDeliveredDate(String deliveredDate) {
            this.deliveredDate = deliveredDate;
        }

        public String getReadydate() {
            return readydate;
        }

        public void setReadydate(String readydate) {
            this.readydate = readydate;
        }

        public String getPreparingdate() {
            return preparingdate;
        }

        public void setPreparingdate(String preparingdate) {
            this.preparingdate = preparingdate;
        }

        public String getConfirmeddate() {
            return confirmeddate;
        }

        public void setConfirmeddate(String confirmeddate) {
            this.confirmeddate = confirmeddate;
        }

        public String getCompletedate() {
            return completedate;
        }

        public void setCompletedate(String completedate) {
            this.completedate = completedate;
        }

        public String getCancelleddate() {
            return cancelleddate;
        }

        public void setCancelleddate(String cancelleddate) {
            this.cancelleddate = cancelleddate;
        }

        public String getPaymentFileUrl() {
            return paymentFileUrl;
        }

        public void setPaymentFileUrl(String paymentFileUrl) {
            this.paymentFileUrl = paymentFileUrl;
        }

        public String getReceivedBy() {
            return receivedBy;
        }

        public void setReceivedBy(String receivedBy) {
            this.receivedBy = receivedBy;
        }

        public String getReceivedMobileNO() {
            return receivedMobileNO;
        }

        public void setReceivedMobileNO(String receivedMobileNO) {
            this.receivedMobileNO = receivedMobileNO;
        }

        public String getDeliveryComment() {
            return deliveryComment;
        }

        public void setDeliveryComment(String deliveryComment) {
            this.deliveryComment = deliveryComment;
        }

        public String getConfirmBy() {
            return confirmBy;
        }

        public void setConfirmBy(String confirmBy) {
            this.confirmBy = confirmBy;
        }

        public String getDeliveryBy() {
            return deliveryBy;
        }

        public void setDeliveryBy(String deliveryBy) {
            this.deliveryBy = deliveryBy;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
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

        public String getContactPersonMobileNo() {
            return contactPersonMobileNo;
        }

        public void setContactPersonMobileNo(String contactPersonMobileNo) {
            this.contactPersonMobileNo = contactPersonMobileNo;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getContactAddress() {
            return contactAddress;
        }

        public void setContactAddress(String contactAddress) {
            this.contactAddress = contactAddress;
        }

        public Integer getAddressID() {
            return addressID;
        }

        public void setAddressID(Integer addressID) {
            this.addressID = addressID;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

    }
}
