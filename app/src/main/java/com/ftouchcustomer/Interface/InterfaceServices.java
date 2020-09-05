package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Appointment.NewAppointment.ClsServicesList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface InterfaceServices {

    @GET
    Call<List<ClsServicesList>> ServiceList(@Url String ServiceUrl);
}
