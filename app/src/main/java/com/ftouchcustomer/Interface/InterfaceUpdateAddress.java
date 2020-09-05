package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Address.postAddress.ClsCustomerAddress;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceUpdateAddress {

    @POST("CustomerV1/UpdateCustomerAddress")
    Call<ClsCustomerAddress> UpdateAddress(@Body ClsCustomerAddress clsCustomerAddress);

}
