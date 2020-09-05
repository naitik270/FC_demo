package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Address.getAddress.ClsGetCustomerAddressResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceGetCustomerAddress {

    @GET("CustomerV1/GetCustomerAddress")
    Call<ClsGetCustomerAddressResponse> GetCustomerAddress(@Query("MobileNumber") String moNumber);

}
