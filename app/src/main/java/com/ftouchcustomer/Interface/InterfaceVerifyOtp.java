package com.ftouchcustomer.Interface;


import com.ftouchcustomer.OTP.ClsVerifyOtp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceVerifyOtp {

    @POST("CustomerV1/CustomerMobileVerificationCode")
    Call<ClsVerifyOtp> postVerifyOtp(@Body ClsVerifyOtp objClsVerifyOtp);
}
