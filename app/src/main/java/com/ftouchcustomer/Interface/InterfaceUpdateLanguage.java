package com.ftouchcustomer.Interface;


import com.ftouchcustomer.Language.ClsUpdateLanguage;
import com.ftouchcustomer.Language.ClsUpdateLanguageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceUpdateLanguage {

    @POST("CustomerV1/UpdateLanguagePreference")
    Call<ClsUpdateLanguageResponse> UpdateLanguage(@Body ClsUpdateLanguage clsUpdateLanguage);

}
