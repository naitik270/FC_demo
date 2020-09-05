package com.ftouchcustomer.Retrofit;

import com.ftouchcustomer.Profile.City.ClsCityResponce;
import com.ftouchcustomer.Profile.State.ClsStateResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ContryInterface {

    @GET("State/GetStateList")
    Call<ClsStateResponce> GetStateList(@Query("CountryID") int CountryID);

    @GET("State/GetCityList")
    Call<ClsCityResponce> GetCityList(@Query("StateID") int StateID);


}
