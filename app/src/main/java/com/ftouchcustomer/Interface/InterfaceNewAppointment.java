package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Appointment.NewAppointment.ClsNewAppointments;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceNewAppointment {

    @POST("Appointment/NewAppointment")
    Call<ClsNewAppointments> NewAppointments(@Body ClsNewAppointments clsNewAppointments);
}
