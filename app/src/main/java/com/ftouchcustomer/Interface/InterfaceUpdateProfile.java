package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfile;
import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceUpdateProfile {

    @POST("CustomerV1/updateProfile")
    Call<ClsUpdateProfileResponse> UpdateProfile(@Body ClsUpdateProfile clsUpdateProfile);

}
