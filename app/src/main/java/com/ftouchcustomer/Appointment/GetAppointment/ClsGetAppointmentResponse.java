package com.ftouchcustomer.Appointment.GetAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsGetAppointmentResponse {

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
    private List<ClsGetAppointmentResponseList> data = null;

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

    public List<ClsGetAppointmentResponseList> getData() {
        return data;
    }

    public void setData(List<ClsGetAppointmentResponseList> data) {
        this.data = data;
    }
}
