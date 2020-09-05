package com.ftouchcustomer.Merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsCatagoryList {

    @SerializedName("Category")
    @Expose
    private String category;

    Boolean selected=false;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
