package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Orders.ClsLayerItemMaster;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceMain {


    @GET("Uploads/Document/Customer/{userCode}/Menu/Menu.json")
    Call<List<ClsLayerItemMaster>> GetMenuList(@Path("userCode") String userCode);


    @GET("Uploads/Document/Customer/{userCode}")
    Call<List<ClsLayerItemMaster>> GetMenuImageList(@Path("userCode") String userCode);

}
