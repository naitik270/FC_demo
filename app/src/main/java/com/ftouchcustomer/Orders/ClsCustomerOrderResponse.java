package com.ftouchcustomer.Orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsCustomerOrderResponse {


    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("data")
    @Expose
    private List<ClsCustomerOrderResponseList> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<ClsCustomerOrderResponseList> getData() {
        return data;
    }

    public void setData(List<ClsCustomerOrderResponseList> data) {
        this.data = data;
    }




}
