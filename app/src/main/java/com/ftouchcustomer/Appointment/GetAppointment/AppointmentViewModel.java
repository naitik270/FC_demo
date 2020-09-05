package com.ftouchcustomer.Appointment.GetAppointment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Appointment.CancleAppointment.ClsCancelAppointment;
import com.ftouchcustomer.Appointment.NewAppointment.ClsNewAppointments;
import com.ftouchcustomer.Repository.Repository;


public class AppointmentViewModel extends AndroidViewModel {

    private Repository repository;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }


    public LiveData<ClsNewAppointments> NewAppointments(ClsNewAppointments clsNewAppointments) {
        return repository.NewAppointments(clsNewAppointments);
    }

    public LiveData<ClsGetAppointmentResponse> getAppointment(ClsGetAppointment clsGetAppointment) {
        return repository.getAppointment(clsGetAppointment);
    }

    public LiveData<ClsCancelAppointment> cancelAppointment(ClsCancelAppointment clsCancelAppointment) {
        return repository.cancelAppointment(clsCancelAppointment);
    }

}
