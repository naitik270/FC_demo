package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Profile.City.ClsCityResponce;
import com.ftouchcustomer.Profile.Country.ClsCountryResponse;
import com.ftouchcustomer.Profile.State.ClsStateResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceCountryStateCity {

    @GET("Country/GetCountryList")
    Call<ClsCountryResponse> GetCountryList();

    @GET("State/GetStateList")
    Call<ClsStateResponce> GetStateList(@Query("CountryID") int CountryID);

    @GET("State/GetCityList")
    Call<ClsCityResponce> GetCityList(@Query("StateID") int StateID);

}
