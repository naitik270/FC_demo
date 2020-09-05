package com.ftouchcustomer.Payment;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfaceMakePayment {

    @Multipart
    @POST("CustomerV1/UpdateOrderPayment")
    Call<ClsCustomerPaymentParams> getCustomerMakePayment(@Part MultipartBody.Part file,
                                                          @Part("OrderID") RequestBody OrderID,
                                                          @Part("CustomerID") RequestBody CustomerID,
                                                          @Part("CustomerMobileNo") RequestBody CustomerMobileNo,
                                                          @Part("MerchantID") RequestBody MerchantID,
                                                          @Part("MerchantCode") RequestBody MerchantCode,
                                                          @Part("PaymentReferenceNo") RequestBody PaymentReferenceNo,
                                                          @Part("PaymentStatus") RequestBody PaymentStatus);



    @POST("CustomerV1/UpdateOrderPayment")
    Call<ClsCustomerPaymentParams> postCustomerMakePayment(@Body ClsCustomerPaymentParams obj);


}
