package com.ftouchcustomer.Appointment.NewAppointment;

public class ClsService {

    public ClsService(String serviceName) {
        ServiceName = serviceName;
    }

    public ClsService(String serviceName, String price) {
        ServiceName = serviceName;
        Price = price;
    }

    private String ServiceName;
    private String Price;
    Boolean selected=false;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
