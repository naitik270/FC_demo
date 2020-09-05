package com.ftouchcustomer.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsTiming implements Serializable {

    @SerializedName("WeekDay")
    @Expose
    private String weekDay = "";

    @SerializedName("Time")
    @Expose
    private String time = "";

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
