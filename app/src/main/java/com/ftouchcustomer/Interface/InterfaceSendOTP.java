package com.ftouchcustomer.Interface;


import com.ftouchcustomer.OTP.ClsSendOtp;
import com.ftouchcustomer.OTP.ClsSendOtpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceSendOTP {

    @POST("CustomerV1/MobileSendOTP")
    Call<ClsSendOtpResponse> postSendOtp(@Body ClsSendOtp objClsSendOtp);
}
