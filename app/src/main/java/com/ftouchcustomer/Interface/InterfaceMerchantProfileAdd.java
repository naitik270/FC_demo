package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Merchant.ClsMerchantProfileAdd;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceMerchantProfileAdd {

    @POST("CustomerV1/MerchantProfileRequestAdd")
    Call<ClsMerchantProfileAdd> MerchantProfileAdd(@Body ClsMerchantProfileAdd clsMerchantProfileAdd);
}
