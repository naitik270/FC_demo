package com.ftouchcustomer.PlaceOrder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfacePlaceOrder {

    @Multipart
    @POST("CustomerV1/CustomerOrderInsert")
    Call<ClsPlaceOrderResponse> placeOrderAPI(@Part MultipartBody.Part file,
                                              @Part("CustomerID") RequestBody CustomerID,
                                              @Part("MobileNo") RequestBody MobileNo,
                                              @Part("MerchantID") RequestBody MerchantID,
                                              @Part("MerchantCode") RequestBody MerchantCode,
                                              @Part("DeliveryType") RequestBody DeliveryType,
                                              @Part("Items") RequestBody Items,
                                              @Part("AddressID") RequestBody AddressID,
                                              @Part("Comment") RequestBody Comment,
                                              @Part("PaymentMethod") RequestBody PaymentMethod,
                                              @Part("PaymentDate") RequestBody PaymentDate,
                                              @Part("PaymentStatus") RequestBody PaymentStatus,
                                              @Part("PaymentReferenceNo") RequestBody PaymentReferenceNo,
                                              @Part("DeliveryCharges") RequestBody DeliveryCharges,
                                              @Part("TotalAmount") RequestBody TotalAmount,
                                              @Part("GrandTotal") RequestBody GrandTotal);

}
