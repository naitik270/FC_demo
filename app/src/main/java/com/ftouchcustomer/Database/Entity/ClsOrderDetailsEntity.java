package com.ftouchcustomer.Database.Entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "OrderDetails")
public class ClsOrderDetailsEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "OrderDetailID")
    @NonNull
    private int OrderDetailID;

    @ColumnInfo(name = "OrderID")
    private String OrderID;

    @ColumnInfo(name = "OrderNo")
    private String OrderNo;

    @ColumnInfo(name = "ItemCode")
    private String ItemCode;

    @ColumnInfo(name = "Item")
    private String Item;

    @ColumnInfo(name = "ItemComment")
    private String ItemComment;

    @ColumnInfo(name = "Rate")
    private double Rate;

    @ColumnInfo(name = "SaleRate")
    private double SaleRate;

    @ColumnInfo(name = "SaleRateWithoutTax")
    private double SaleRateWithoutTax;

    @ColumnInfo(name = "Quantity")
    private double Quantity;

    @ColumnInfo(name = "Discount_per")
    private double Discount_per;

    @ColumnInfo(name = "Discount_amt")
    private double Discount_amt;

    @ColumnInfo(name = "Amount")
    private double Amount;

    @ColumnInfo(name = "CGST")
    private double CGST;

    @ColumnInfo(name = "SGST")
    private double SGST;

    @ColumnInfo(name = "IGST")
    private double IGST;

    @ColumnInfo(name = "TotalTaxAmount")
    private double TotalTaxAmount;

    @ColumnInfo(name = "GrandTotal")
    private double GrandTotal;

    @ColumnInfo(name = "SaveStatus")
    private String SaveStatus;

    @ColumnInfo(name = "EntryDate")
    private Date EntryDate;

    @ColumnInfo(name = "HSN_SAC_CODE")
    private String HSN_SAC_CODE;

    @ColumnInfo(name = "CustomerCode")
    private String CustomerCode;

    @ColumnInfo(name = "CustomerID")
    private int CustomerID;

    @ColumnInfo(name = "UNIT")
    private String UNIT;

    @ColumnInfo(name = "LAYERITEM_ID")
    private String LAYERITEM_ID;

    @ColumnInfo(name = "MerchantName")
    private String MerchantName;

    @Ignore
    private int srNo;

    @Ignore
    private Double totalAmountMerchantWise;

    @Ignore
    private boolean isHeader = false;

    public ClsOrderDetailsEntity() {
    }

    @Ignore
    public ClsOrderDetailsEntity(String customerCode, String merchantName) {
        this.CustomerCode = customerCode;
        this.MerchantName = merchantName;
    }

    public Double getTotalAmountMerchantWise() {
        return totalAmountMerchantWise;
    }

    public void setTotalAmountMerchantWise(Double totalAmountMerchantWise) {
        this.totalAmountMerchantWise = totalAmountMerchantWise;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getLAYERITEM_ID() {
        return LAYERITEM_ID;
    }

    public void setLAYERITEM_ID(String LAYERITEM_ID) {
        this.LAYERITEM_ID = LAYERITEM_ID;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String UNIT) {
        this.UNIT = UNIT;
    }


    public String getHSN_SAC_CODE() {
        return HSN_SAC_CODE;
    }

    public void setHSN_SAC_CODE(String HSN_SAC_CODE) {
        this.HSN_SAC_CODE = HSN_SAC_CODE;
    }

    public int getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        OrderDetailID = orderDetailID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getItemComment() {
        return ItemComment;
    }

    public void setItemComment(String itemComment) {
        ItemComment = itemComment;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public double getSaleRate() {
        return SaleRate;
    }

    public void setSaleRate(double saleRate) {
        SaleRate = saleRate;
    }

    public double getSaleRateWithoutTax() {
        return SaleRateWithoutTax;
    }

    public void setSaleRateWithoutTax(double saleRateWithoutTax) {
        SaleRateWithoutTax = saleRateWithoutTax;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getDiscount_per() {
        return Discount_per;
    }

    public void setDiscount_per(double discount_per) {
        Discount_per = discount_per;
    }

    public double getDiscount_amt() {
        return Discount_amt;
    }

    public void setDiscount_amt(double discount_amt) {
        Discount_amt = discount_amt;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getCGST() {
        return CGST;
    }

    public void setCGST(double CGST) {
        this.CGST = CGST;
    }

    public double getSGST() {
        return SGST;
    }

    public void setSGST(double SGST) {
        this.SGST = SGST;
    }

    public double getIGST() {
        return IGST;
    }

    public void setIGST(double IGST) {
        this.IGST = IGST;
    }

    public double getTotalTaxAmount() {
        return TotalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        TotalTaxAmount = totalTaxAmount;
    }

    public double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getSaveStatus() {
        return SaveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        SaveStatus = saveStatus;
    }

    public Date getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(Date entryDate) {
        EntryDate = entryDate;
    }


}
