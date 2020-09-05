package com.ftouchcustomer.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsMerchantResponse {



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
    private List<ClsMerchantResponseList> data = null;

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

    public List<ClsMerchantResponseList> getData() {
        return data;
    }

    public void setData(List<ClsMerchantResponseList> data) {
        this.data = data;
    }


}
