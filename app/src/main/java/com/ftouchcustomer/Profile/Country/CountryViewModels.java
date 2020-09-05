package com.ftouchcustomer.Profile.Country;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ftouchcustomer.Profile.City.ClsCityResponce;
import com.ftouchcustomer.Profile.State.ClsStateResponce;
import com.ftouchcustomer.Profile.getProfile.ClsGetProfileResponse;
import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfile;
import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfileResponse;
import com.ftouchcustomer.Repository.Repository;

public class CountryViewModels extends AndroidViewModel {
    private Repository repository;

    public CountryViewModels(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<ClsCountryResponse> getCountryResponse() {

        return repository.getCountry();
    }

    public LiveData<ClsStateResponce> getStateResponse(int CountryID) {
        return repository.getState(CountryID);
    }

    public LiveData<ClsCityResponce> getCityResponse(int StateID) {
        return repository.getCity(StateID);
    }

    public LiveData<ClsGetProfileResponse> getProfile(String moNumber, String email) {
        return repository.getProfile(moNumber, email);
    }

    public LiveData<ClsUpdateProfileResponse> updateProfile(ClsUpdateProfile clsUpdateProfile) {
        return repository.updateProfile(clsUpdateProfile);
    }
}
