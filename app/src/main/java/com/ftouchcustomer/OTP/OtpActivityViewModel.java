package com.ftouchcustomer.OTP;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Repository.Repository;

public class OtpActivityViewModel extends AndroidViewModel {
    private Repository repository;

    public OtpActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsSendOtpResponse> sendOtp(ClsSendOtp clsSendOtp) {
        return repository.sendOtp(clsSendOtp);
    }

    public LiveData<ClsVerifyOtp> verifyOtp(ClsVerifyOtp clsVerifyOtp) {
        return repository.verifyOtp(clsVerifyOtp);
    }

    public LiveData<ClsSendVerificationEmail> verifyEmail(ClsSendVerificationEmail clsSendVerificationEmail) {
        return repository.verifyEmail(clsSendVerificationEmail);
    }
}
