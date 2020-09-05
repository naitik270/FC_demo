package com.ftouchcustomer.Interface;


import com.ftouchcustomer.Profile.getProfile.ClsGetProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceGetProfile {

    @GET("CustomerV1/GetProfile")
    Call<ClsGetProfileResponse> GetProfile(@Query("MobileNumber") String moNumber,
                                           @Query("EMail") String email);

}
