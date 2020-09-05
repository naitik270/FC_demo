package com.ftouchcustomer.Appointment.GetAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsGetServices {

    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("ApplyTax")
    @Expose
    private String applyTax;
    @SerializedName("ApproxServiceMinute")
    @Expose
    private Integer approxServiceMinute;
    @SerializedName("AvailableDay")
    @Expose
    private String availableDay;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Images_name_list")
    @Expose
    private List<String> imagesNameList = null;
    @SerializedName("Rate")
    @Expose
    private Integer rate;
    @SerializedName("ServiceCode")
    @Expose
    private String serviceCode;
    @SerializedName("ServiceId")
    @Expose
    private Integer serviceId;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("TaxSlabId")
    @Expose
    private Integer taxSlabId;
    @SerializedName("TaxType")
    @Expose
    private String taxType;
    @SerializedName("featureName_list")
    @Expose
    private List<String> featureNameList = null;
    @SerializedName("featuresList")
    @Expose
    private List<Object> featuresList = null;
    @SerializedName("isSelected")
    @Expose
    private Boolean isSelected;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getApplyTax() {
        return applyTax;
    }

    public void setApplyTax(String applyTax) {
        this.applyTax = applyTax;
    }

    public Integer getApproxServiceMinute() {
        return approxServiceMinute;
    }

    public void setApproxServiceMinute(Integer approxServiceMinute) {
        this.approxServiceMinute = approxServiceMinute;
    }

    public String getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagesNameList() {
        return imagesNameList;
    }

    public void setImagesNameList(List<String> imagesNameList) {
        this.imagesNameList = imagesNameList;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getTaxSlabId() {
        return taxSlabId;
    }

    public void setTaxSlabId(Integer taxSlabId) {
        this.taxSlabId = taxSlabId;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public List<String> getFeatureNameList() {
        return featureNameList;
    }

    public void setFeatureNameList(List<String> featureNameList) {
        this.featureNameList = featureNameList;
    }

    public List<Object> getFeaturesList() {
        return featuresList;
    }

    public void setFeaturesList(List<Object> featuresList) {
        this.featuresList = featuresList;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}
