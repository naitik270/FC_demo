package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Merchant.ClsCategoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceCategoriesList {

    @GET("CustomerV1/GetBusinessCategory")
    Call<ClsCategoriesResponse> GetCategoriesList(@Query("CustomerCode") String CustomerCode,
                                                  @Query("Mode") String Mode);
}
