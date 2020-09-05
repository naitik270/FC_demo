package com.ftouchcustomer.Database.Entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "OrderMaster")
public class OrderMasterEntity {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MerchantID")
    @NonNull
    private int MerchantID;

    @ColumnInfo(name = "MerchantCode")
    private String MerchantCode;

    @ColumnInfo(name = "MobileNo")
    private String MobileNo;

    @ColumnInfo(name = "CustomerName")
    private String CustomerName;

    @ColumnInfo(name = "CompanyName")
    private String CompanyName;

    @ColumnInfo(name = "GSTNo")
    private String GSTNo;

    @ColumnInfo(name = "BillDate")
    private Date BillDate;

    @ColumnInfo(name = "OrderNo")
    private String OrderNo;

    @ColumnInfo(name = "SaleType")
    private String SaleType;

    @ColumnInfo(name = "SaleReturnDiscount")
    private double SaleReturnDiscount;

    @ColumnInfo(name = "TotalAmount")
    private double TotalAmount;

    @ColumnInfo(name = "DiscountAmount")
    private double DiscountAmount;

    @ColumnInfo(name = "TotalPaybleAmount")
    private double TotalPaybleAmount;

    @ColumnInfo(name = "TotalTaxAmount")
    private double TotalTaxAmount;

    @ColumnInfo(name = "TotalReceiveableAmount")
    private double TotalReceiveableAmount;

    @ColumnInfo(name = "PaidAmount")
    private double PaidAmount;

    @ColumnInfo(name = "AdjumentAmount")
    private double AdjumentAmount;

    @ColumnInfo(name = "EntryDate")
    private Date EntryDate;

    @ColumnInfo(name = "ApplyTax")
    private String ApplyTax;

    @ColumnInfo(name = "PaymentMode")
    private String PaymentMode;

    @ColumnInfo(name = "PaymentDetail")
    private String PaymentDetail;

    @ColumnInfo(name = "Different_Amount_mode")
    private String Different_Amount_mode;

    @ColumnInfo(name = "BillTo")
    private String BillTo;

    @ColumnInfo(name = "Sms_Limit")
    private int Sms_Limit;

    @ColumnInfo(name = "QuotationId")
    private int QuotationId;

    public int getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(int merchantID) {
        MerchantID = merchantID;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getGSTNo() {
        return GSTNo;
    }

    public void setGSTNo(String GSTNo) {
        this.GSTNo = GSTNo;
    }

    public Date getBillDate() {
        return BillDate;
    }

    public void setBillDate(Date billDate) {
        BillDate = billDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getSaleType() {
        return SaleType;
    }

    public void setSaleType(String saleType) {
        SaleType = saleType;
    }

    public double getSaleReturnDiscount() {
        return SaleReturnDiscount;
    }

    public void setSaleReturnDiscount(double saleReturnDiscount) {
        SaleReturnDiscount = saleReturnDiscount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public double getTotalPaybleAmount() {
        return TotalPaybleAmount;
    }

    public void setTotalPaybleAmount(double totalPaybleAmount) {
        TotalPaybleAmount = totalPaybleAmount;
    }

    public double getTotalTaxAmount() {
        return TotalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        TotalTaxAmount = totalTaxAmount;
    }

    public double getTotalReceiveableAmount() {
        return TotalReceiveableAmount;
    }

    public void setTotalReceiveableAmount(double totalReceiveableAmount) {
        TotalReceiveableAmount = totalReceiveableAmount;
    }

    public double getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        PaidAmount = paidAmount;
    }

    public double getAdjumentAmount() {
        return AdjumentAmount;
    }

    public void setAdjumentAmount(double adjumentAmount) {
        AdjumentAmount = adjumentAmount;
    }

    public Date getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(Date entryDate) {
        EntryDate = entryDate;
    }

    public String getApplyTax() {
        return ApplyTax;
    }

    public void setApplyTax(String applyTax) {
        ApplyTax = applyTax;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getPaymentDetail() {
        return PaymentDetail;
    }

    public void setPaymentDetail(String paymentDetail) {
        PaymentDetail = paymentDetail;
    }

    public String getDifferent_Amount_mode() {
        return Different_Amount_mode;
    }

    public void setDifferent_Amount_mode(String different_Amount_mode) {
        Different_Amount_mode = different_Amount_mode;
    }

    public String getBillTo() {
        return BillTo;
    }

    public void setBillTo(String billTo) {
        BillTo = billTo;
    }

    public int getSms_Limit() {
        return Sms_Limit;
    }

    public void setSms_Limit(int sms_Limit) {
        Sms_Limit = sms_Limit;
    }

    public int getQuotationId() {
        return QuotationId;
    }

    public void setQuotationId(int quotationId) {
        QuotationId = quotationId;
    }
}
