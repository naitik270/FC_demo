package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Address.defaultAddress.ClsDefaultAddress;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceDefaultAddress {

    @POST("CustomerV1/MakeCustomerAddressDefault")
    Call<ClsDefaultAddress> defaultAddress(@Body ClsDefaultAddress clsDefaultAddress);

}
