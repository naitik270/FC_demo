package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Complain.ClsComplainSuccess;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceComplain {


    @GET("ComplainDisposition/GetComplainDisposition")
    Call<ClsComplainSuccess> value(@Query("ProductName") String ProductName);

}
