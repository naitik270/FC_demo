package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Merchant.ClsFavoriteMerchantParams;
import com.ftouchcustomer.Merchant.ClsFavoriteMerchantResponse;
import com.ftouchcustomer.Orders.ClsCustomerOrderDetail;
import com.ftouchcustomer.Orders.ClsCustomerOrderListParams;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponse;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodResponse;
import com.ftouchcustomer.Orders.ClsPlaceOrderDeleteResponse;
import com.ftouchcustomer.PlaceOrder.ClsCancelOrderParams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface InterfaceGetOrderList {

    @POST("CustomerV1/GetCustomerOrderList")
    Call<ClsCustomerOrderResponse> getCustomerOrderListAPI(@Body ClsCustomerOrderListParams obj);

    @GET("CustomerV1/CustomerOrderDetails")
    Call<ClsCustomerOrderDetail> getOnlineOrdersDetails(@Query("Orderid") int OrderId,
                                                        @Query("Customerid") int Customerid,
                                                        @Query("CustomerMobileNo") String CustomerMobileNo);

    @POST("CustomerV1/CustomerOrderCancelled")
    Call<ClsPlaceOrderDeleteResponse> deletePlaceOrder(@Body ClsCancelOrderParams obj);

    @GET("CustomerV1/GetMerchantPaymentMethod")
    Call<ClsGetMerchantPaymentMethodResponse>
    getMerchantPaymentMethod(@Query("MerchantCode") String MerchantCode);

    @GET
    Call<List<ClsOrderDetailsEntity>> getOnlineOrderItems(@Url String url);

    @POST("CustomerV1/FavoriteMerchant")
    Call<ClsFavoriteMerchantResponse> favoriteMerchant(@Body ClsFavoriteMerchantParams obj);

}
