package com.ftouchcustomer.Interface;

import com.ftouchcustomer.Complain.ClsCustomerComplainParams;
import com.ftouchcustomer.Payment.ClsCustomerPaymentParams;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfaceCustomerComplain {

    @POST("ComplainDisposition/RegisterCustomerComplain")
    Call<ClsCustomerComplainParams> postComplain(@Body ClsCustomerComplainParams obj);
//



   /* @Multipart
    @POST("ComplainDisposition/RegisterCustomerComplain")
    Call<ClsCustomerComplainParams> postComplain(@Part MultipartBody.Part file,
                                                          @Part("MobileNumber") RequestBody MobileNumber,
                                                          @Part("CustomerCode") RequestBody CustomerCode,
                                                          @Part("RequestSubject") RequestBody RequestSubject,
                                                          @Part("RequestRemark") RequestBody RequestRemark,
                                                          @Part("ProductName") RequestBody ProductName,
                                                          @Part("FileName") RequestBody FileName,
                                                          @Part("FileExtension") RequestBody FileExtension,
                                                          @Part("ApplicationType") RequestBody ApplicationType);

*/


}
