package com.ftouchcustomer.Database.GetSetValue;

import androidx.room.ColumnInfo;

public class ClsOrderDetail {

    @ColumnInfo(name = "total")
    public double total = 0.0;
    @ColumnInfo(name = "OrderNo")
    public String OrderNo = "";
    @ColumnInfo(name = "item_count")
    public int item_count = 0;
    @ColumnInfo(name = "OrderDetailID")
    public int OrderDetailID = 0;
    @ColumnInfo(name = "CustomerCode")
    private String CustomerCode;
    @ColumnInfo(name = "CustomerID")
    private int CustomerID;

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

    public int getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        OrderDetailID = orderDetailID;
    }

    public ClsOrderDetail() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        this.OrderNo = orderNo;
    }

    public int getSize() {
        return item_count;
    }

    public void setSize(int size) {
        this.item_count = size;
    }
}

