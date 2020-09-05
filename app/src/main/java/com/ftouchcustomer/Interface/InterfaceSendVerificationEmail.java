package com.ftouchcustomer.Interface;

import com.ftouchcustomer.OTP.ClsSendVerificationEmail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceSendVerificationEmail {

    @POST("CustomerV1/SendVerificationEmail")
    Call<ClsSendVerificationEmail> postSendVerificationEmail(@Body ClsSendVerificationEmail clsSendVerificationEmail);

}
