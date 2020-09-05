package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Merchant.ClsMerchantParams;
import com.ftouchcustomer.Merchant.ClsMerchantResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceGetMerchantList {

    @POST("CustomerV1/GetMerchantList")
    Call<ClsMerchantResponse> GetMerchantList(@Body ClsMerchantParams objClsMerchantParams);

}
