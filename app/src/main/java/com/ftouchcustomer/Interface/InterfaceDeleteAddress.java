package com.ftouchcustomer.Interface;


import com.ftouchcustomer.Address.deleteAddress.ClsDeleteAddress;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceDeleteAddress {

    @POST("CustomerV1/DeleteCustomerAddress")
    Call<ClsDeleteAddress> DeleteCustomerAddress(@Body ClsDeleteAddress obj);
}
