package com.ftouchcustomer.Interface;



import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceGetAppointment {

    @POST("Appointment/GetAppointment")
    Call<ClsGetAppointmentResponse> GetAppointment(@Body ClsGetAppointment clsGetAppointment);

}
