package com.ftouchcustomer.Interface;


import com.ftouchcustomer.Appointment.CancleAppointment.ClsCancelAppointment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceCancelAppoinment {

    @POST("Appointment/CancelAppointment")
    Call<ClsCancelAppointment> CancelAppointment(@Body ClsCancelAppointment clsCancelAppointment);
}
