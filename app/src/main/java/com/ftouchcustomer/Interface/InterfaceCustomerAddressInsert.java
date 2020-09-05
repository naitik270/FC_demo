package com.ftouchcustomer.Interface;


import com.ftouchcustomer.Address.postAddress.ClsCustomerAddress;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceCustomerAddressInsert {

    @POST("CustomerV1/CustomerAddressInsert")
    Call<ClsCustomerAddress> PostCustomerAddress(@Body ClsCustomerAddress clsCustomerAddress);

}
