package com.ftouchcustomer.Repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ftouchcustomer.Appointment.CancleAppointment.ClsCancelAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointment;
import com.ftouchcustomer.Appointment.GetAppointment.ClsGetAppointmentResponse;
import com.ftouchcustomer.Appointment.NewAppointment.ClsNewAppointments;
import com.ftouchcustomer.Classes.ClsTotalAmountCustomerCodeWise;
import com.ftouchcustomer.Address.defaultAddress.ClsDefaultAddress;
import com.ftouchcustomer.Address.deleteAddress.ClsDeleteAddress;
import com.ftouchcustomer.Address.getAddress.ClsGetCustomerAddressResponse;
import com.ftouchcustomer.Address.postAddress.ClsCustomerAddress;
import com.ftouchcustomer.Database.AppDatabase;
import com.ftouchcustomer.Database.AppExecutor;
import com.ftouchcustomer.Database.Entity.ClsOrderDetailsEntity;
import com.ftouchcustomer.Database.GetSetValue.ClsOrderDetail;
import com.ftouchcustomer.Interface.InterfaceCancelAppoinment;
import com.ftouchcustomer.Interface.InterfaceCategoriesList;
import com.ftouchcustomer.Interface.InterfaceCustomerAddressInsert;
import com.ftouchcustomer.Interface.InterfaceDefaultAddress;
import com.ftouchcustomer.Interface.InterfaceDeleteAddress;
import com.ftouchcustomer.Interface.InterfaceGetAppointment;
import com.ftouchcustomer.Interface.InterfaceGetCustomerAddress;
import com.ftouchcustomer.Interface.InterfaceGetMerchantList;
import com.ftouchcustomer.Global.ApiClient;
import com.ftouchcustomer.Interface.InterfaceCountryStateCity;
import com.ftouchcustomer.Interface.InterfaceGetOrderList;
import com.ftouchcustomer.Interface.InterfaceGetProfile;
import com.ftouchcustomer.Interface.InterfaceMain;
import com.ftouchcustomer.Interface.InterfaceMerchantProfileAdd;
import com.ftouchcustomer.Interface.InterfaceNewAppointment;
import com.ftouchcustomer.Interface.InterfaceSendOTP;
import com.ftouchcustomer.Interface.InterfaceSendVerificationEmail;
import com.ftouchcustomer.Interface.InterfaceUpdateAddress;
import com.ftouchcustomer.Interface.InterfaceUpdateLanguage;
import com.ftouchcustomer.Interface.InterfaceUpdateProfile;
import com.ftouchcustomer.Interface.InterfaceVerifyOtp;
import com.ftouchcustomer.Language.ClsUpdateLanguage;
import com.ftouchcustomer.Language.ClsUpdateLanguageResponse;
import com.ftouchcustomer.Merchant.ClsCategoriesResponse;
import com.ftouchcustomer.Merchant.ClsFavoriteMerchantParams;
import com.ftouchcustomer.Merchant.ClsFavoriteMerchantResponse;
import com.ftouchcustomer.Merchant.ClsMerchantParams;
import com.ftouchcustomer.Merchant.ClsMerchantProfileAdd;
import com.ftouchcustomer.Merchant.ClsMerchantResponse;
import com.ftouchcustomer.OTP.ClsSendOtp;
import com.ftouchcustomer.OTP.ClsSendOtpResponse;
import com.ftouchcustomer.OTP.ClsSendVerificationEmail;
import com.ftouchcustomer.OTP.ClsVerifyOtp;
import com.ftouchcustomer.Orders.ClsCustomerOrderListParams;
import com.ftouchcustomer.Orders.ClsCustomerOrderResponse;
import com.ftouchcustomer.Orders.ClsGetMerchantPaymentMethodResponse;
import com.ftouchcustomer.Orders.ClsLayerItemMaster;
import com.ftouchcustomer.Orders.ClsPlaceOrderDeleteResponse;
import com.ftouchcustomer.PlaceOrder.ClsCancelOrderParams;
import com.ftouchcustomer.Profile.City.ClsCityResponce;
import com.ftouchcustomer.Profile.Country.ClsCountryResponse;
import com.ftouchcustomer.Profile.State.ClsStateResponce;
import com.ftouchcustomer.Profile.getProfile.ClsGetProfileResponse;
import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfile;
import com.ftouchcustomer.Profile.updateProfile.ClsUpdateProfileResponse;
import com.ftouchcustomer.Retrofit.ContryInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java8.util.OptionalDouble;
import java8.util.stream.StreamSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private Context context;
    List<String> headerlist = new ArrayList<>();

    public LiveData<ClsCountryResponse> getCountry() {

        final MutableLiveData<ClsCountryResponse> countryResponseList = new MutableLiveData<>();
        InterfaceCountryStateCity myInterface =
                ApiClient.getRetrofitInstance().create(InterfaceCountryStateCity.class);
        Call<ClsCountryResponse> call = myInterface.GetCountryList();

        Log.e("--countries--", "URL: " + call.request().url());

        call.enqueue(new Callback<ClsCountryResponse>() {
            @Override
            public void onResponse(Call<ClsCountryResponse> call, Response<ClsCountryResponse> response) {

                Log.e("--countries--", "onRequestResponse: " + response.code());
                if (response.body() != null && response.code() == 200) {

                    countryResponseList.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----GetSpecialRequestlist---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsCountryResponse> call, Throwable t) {
                Log.e("--countries--", "onFailure: GetSpecialRequestlist" + t.getMessage());
                try {
                    countryResponseList.setValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--countries--", "onFailure: " + t.toString());
            }
        });

        return countryResponseList;
    }

    public LiveData<ClsStateResponce> getState(int countryId) {
        final MutableLiveData<ClsStateResponce> stateResponseList = new MutableLiveData<>();
        ContryInterface myInterface = ApiClient.getRetrofitInstance().create(ContryInterface.class);
        Call<ClsStateResponce> call = myInterface.GetStateList(countryId);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsStateResponce>() {
            @Override
            public void onResponse(Call<ClsStateResponce> call, Response<ClsStateResponce> response) {
                if (response.body() != null && response.code() == 200) {

                    stateResponseList.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----GetWatingPersonList---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsStateResponce> call, Throwable t) {
                try {
                    stateResponseList.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return stateResponseList;
    }

    public LiveData<ClsCityResponce> getCity(int StateID) {
        final MutableLiveData<ClsCityResponce> cityResponseList = new MutableLiveData<>();
        ContryInterface myInterface = ApiClient.getRetrofitInstance().create(ContryInterface.class);
        Call<ClsCityResponce> call = myInterface.GetCityList(StateID);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsCityResponce>() {
            @Override
            public void onResponse(Call<ClsCityResponce> call, Response<ClsCityResponce> response) {

                Log.e("--URL--", "onResponse: " + response.code());
                if (response.body() != null && response.code() == 200) {

                    cityResponseList.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----GetWatingPersonList---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsCityResponce> call, Throwable t) {

                try {
                    cityResponseList.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:GetWatingPersonList " + t.toString());
            }
        });

        return cityResponseList;
    }


    public LiveData<ClsSendOtpResponse> sendOtp(ClsSendOtp clsSendOtp) {
        final MutableLiveData<ClsSendOtpResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceSendOTP myInterface = ApiClient.getRetrofitInstance().create(InterfaceSendOTP.class);

        Call<ClsSendOtpResponse> call = myInterface.postSendOtp(clsSendOtp);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsSendOtpResponse>() {
            @Override
            public void onResponse(Call<ClsSendOtpResponse> call, Response<ClsSendOtpResponse> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----sendOtp---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsSendOtpResponse> call, Throwable t) {
                try {
                    mutableLiveData.setValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:sendOtp " + t.toString());
            }
        });
        return mutableLiveData;
    }

    public LiveData<ClsVerifyOtp> verifyOtp(ClsVerifyOtp clsVerifyOtp) {
        final MutableLiveData<ClsVerifyOtp> mutableLiveData = new MutableLiveData<>();
        InterfaceVerifyOtp myInterface = ApiClient.getRetrofitInstance().create(InterfaceVerifyOtp.class);

        Call<ClsVerifyOtp> call = myInterface.postVerifyOtp(clsVerifyOtp);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsVerifyOtp>() {
            @Override
            public void onResponse(Call<ClsVerifyOtp> call, Response<ClsVerifyOtp> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.postValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----verifyOtp---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsVerifyOtp> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:verifyOtp " + t.toString());
            }
        });
        return mutableLiveData;
    }

    public LiveData<ClsSendVerificationEmail> verifyEmail(ClsSendVerificationEmail clsSendVerificationEmail) {
        final MutableLiveData<ClsSendVerificationEmail> mutableLiveData = new MutableLiveData<>();
        InterfaceSendVerificationEmail myInterface = ApiClient.getRetrofitInstance().create(InterfaceSendVerificationEmail.class);

        Call<ClsSendVerificationEmail> call = myInterface.postSendVerificationEmail(clsSendVerificationEmail);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsSendVerificationEmail>() {
            @Override
            public void onResponse(Call<ClsSendVerificationEmail> call, Response<ClsSendVerificationEmail> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.postValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----verifyEmail---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsSendVerificationEmail> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:verifyEmail " + t.toString());
            }
        });
        return mutableLiveData;
    }

    public LiveData<ClsGetProfileResponse> getProfile(String moNumber, String email) {
        final MutableLiveData<ClsGetProfileResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetProfile myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetProfile.class);
        Call<ClsGetProfileResponse> call = myInterface.GetProfile(moNumber, email);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsGetProfileResponse>() {
            @Override
            public void onResponse(Call<ClsGetProfileResponse> call, Response<ClsGetProfileResponse> response) {

                Log.e("--URL--", "onResponse - getProfile :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----getProfile---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsGetProfileResponse> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : getProfile " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsUpdateProfileResponse> updateProfile(ClsUpdateProfile clsUpdateProfile) {
        final MutableLiveData<ClsUpdateProfileResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceUpdateProfile myInterface = ApiClient.getRetrofitInstance().create(InterfaceUpdateProfile.class);

        Call<ClsUpdateProfileResponse> call = myInterface.UpdateProfile(clsUpdateProfile);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsUpdateProfileResponse>() {
            @Override
            public void onResponse(Call<ClsUpdateProfileResponse> call, Response<ClsUpdateProfileResponse> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.postValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----updateProfile---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsUpdateProfileResponse> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:updateProfile " + t.toString());
            }
        });
        return mutableLiveData;
    }

    public LiveData<ClsUpdateLanguageResponse> updateLanguage(ClsUpdateLanguage clsUpdateLanguage) {
        final MutableLiveData<ClsUpdateLanguageResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceUpdateLanguage myInterface = ApiClient.getRetrofitInstance().create(InterfaceUpdateLanguage.class);

        Call<ClsUpdateLanguageResponse> call = myInterface.UpdateLanguage(clsUpdateLanguage);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsUpdateLanguageResponse>() {
            @Override
            public void onResponse(Call<ClsUpdateLanguageResponse> call, Response<ClsUpdateLanguageResponse> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.postValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----updateLanguage---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsUpdateLanguageResponse> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:updateLanguage " + t.toString());
            }
        });
        return mutableLiveData;
    }


    public LiveData<ClsMerchantResponse> getMerchantList(ClsMerchantParams clsMerchantParams) {
        final MutableLiveData<ClsMerchantResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetMerchantList myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetMerchantList.class);

        Call<ClsMerchantResponse> call = myInterface.GetMerchantList(clsMerchantParams);
        Log.e("--URL--", "MerchantURL: " + call.request().url());

        call.enqueue(new Callback<ClsMerchantResponse>() {
            @Override
            public void onResponse(Call<ClsMerchantResponse> call, Response<ClsMerchantResponse> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.postValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----getMerchantList---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClsMerchantResponse> call, @NonNull Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:updateLanguage " + t.toString());
            }
        });
        return mutableLiveData;
    }

    public LiveData<ClsCustomerAddress> postCustomerAddress(ClsCustomerAddress clsCustomerAddress) {
        final MutableLiveData<ClsCustomerAddress> mutableLiveData = new MutableLiveData<>();
        InterfaceCustomerAddressInsert myInterface = ApiClient.getRetrofitInstance().create(InterfaceCustomerAddressInsert.class);

        Call<ClsCustomerAddress> call = myInterface.PostCustomerAddress(clsCustomerAddress);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsCustomerAddress>() {
            @Override
            public void onResponse(Call<ClsCustomerAddress> call, Response<ClsCustomerAddress> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.postValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----postCustomerAddress---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsCustomerAddress> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:postCustomerAddress " + t.toString());
            }
        });
        return mutableLiveData;
    }

    public LiveData<ClsGetCustomerAddressResponse> getCustomerAddress(String moNumber) {
        final MutableLiveData<ClsGetCustomerAddressResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetCustomerAddress myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetCustomerAddress.class);
        Call<ClsGetCustomerAddressResponse> call = myInterface.GetCustomerAddress(moNumber);

        Log.e("--URL--", "Address: " + call.request().url());

        call.enqueue(new Callback<ClsGetCustomerAddressResponse>() {
            @Override
            public void onResponse(Call<ClsGetCustomerAddressResponse> call, Response<ClsGetCustomerAddressResponse> response) {

                Log.e("--URL--", "Address: " + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "Gson: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsGetCustomerAddressResponse> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : getProfile " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsDeleteAddress> deleteCustomerAddress(ClsDeleteAddress obj) {
        final MutableLiveData<ClsDeleteAddress> mutableLiveData = new MutableLiveData<>();
        InterfaceDeleteAddress myInterface = ApiClient.getRetrofitInstance()
                .create(InterfaceDeleteAddress.class);
        Call<ClsDeleteAddress> call = myInterface.DeleteCustomerAddress(obj);

        Log.e("--URL--", "URL: " + call.request().url());

        call.enqueue(new Callback<ClsDeleteAddress>() {
            @Override
            public void onResponse(@NonNull Call<ClsDeleteAddress> call,
                                   @NonNull Response<ClsDeleteAddress> response) {

             /*   Log.e("--URL--", "onResponse - deleteCustomerAddress :" + response.code());
                if (response.body() != null && response.code() == 200) {


                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----deleteCustomerAddress---: " + jsonInString);
                }*/


                Gson gson = new Gson();
                String jsonInString = gson.toJson(response.body());
                Log.d("Check", "onResponse----deleteCustomerAddress---: " + jsonInString);

                mutableLiveData.setValue(response.body());


            }

            @Override
            public void onFailure(Call<ClsDeleteAddress> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure: " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsCustomerAddress> updateCustomerAddress(ClsCustomerAddress clsCustomerAddress) {
        final MutableLiveData<ClsCustomerAddress> mutableLiveData = new MutableLiveData<>();
        InterfaceUpdateAddress myInterface = ApiClient.getRetrofitInstance().create(InterfaceUpdateAddress.class);
        Call<ClsCustomerAddress> call = myInterface.UpdateAddress(clsCustomerAddress);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsCustomerAddress>() {
            @Override
            public void onResponse(Call<ClsCustomerAddress> call, Response<ClsCustomerAddress> response) {

                Log.e("--URL--", "onResponse - updateCustomerAddress :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----updateCustomerAddress---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsCustomerAddress> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : updateCustomerAddress " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsDefaultAddress> defaultAddress(ClsDefaultAddress clsDefaultAddress) {
        final MutableLiveData<ClsDefaultAddress> mutableLiveData = new MutableLiveData<>();
        InterfaceDefaultAddress myInterface = ApiClient.getRetrofitInstance().create(InterfaceDefaultAddress.class);
        Call<ClsDefaultAddress> call = myInterface.defaultAddress(clsDefaultAddress);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsDefaultAddress>() {
            @Override
            public void onResponse(Call<ClsDefaultAddress> call, Response<ClsDefaultAddress> response) {

                Log.e("--URL--", "onResponse - defaultAddress :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----defaultAddress---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsDefaultAddress> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : defaultAddress " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsNewAppointments> NewAppointments(ClsNewAppointments clsNewAppointments) {
        final MutableLiveData<ClsNewAppointments> mutableLiveData = new MutableLiveData<>();
        InterfaceNewAppointment myInterface = ApiClient.getRetrofitInstance().create(InterfaceNewAppointment.class);
        Call<ClsNewAppointments> call = myInterface.NewAppointments(clsNewAppointments);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsNewAppointments>() {
            @Override
            public void onResponse(Call<ClsNewAppointments> call, Response<ClsNewAppointments> response) {

                Log.e("--URL--", "onResponse - updateCustomerAddress :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----updateCustomerAddress---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsNewAppointments> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : updateCustomerAddress " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<List<ClsLayerItemMaster>> getMenuList(String userCode) {

        final MutableLiveData<List<ClsLayerItemMaster>> mutableLiveData = new MutableLiveData<>();

        InterfaceMain interfaceMain = ApiClient.getDemoInstance().create(InterfaceMain.class);
        Log.e("--Menu--", "interfaceDesignation: " + interfaceMain.toString());
        Call<List<ClsLayerItemMaster>> call = interfaceMain.GetMenuList(userCode);
        Log.e("--Menu--", "URL: " + call.request().url());

        call.enqueue(new Callback<List<ClsLayerItemMaster>>() {
            @Override
            public void onResponse(Call<List<ClsLayerItemMaster>> call, Response<List<ClsLayerItemMaster>> response) {
                Log.e("--Menu--", "response: " + response);
                if (response.body() != null) {
                    Log.e("--Menu--", "IF_response: " + response.body());
                    mutableLiveData.postValue(response.body());

                } else {
                    Log.e("--Menu--", "ElSE_response: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<ClsLayerItemMaster>> call, Throwable t) {
                mutableLiveData.postValue(null);
                Log.e("--Menu--", "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }

    public LiveData<List<ClsLayerItemMaster>> getMenuImageList(String userCode) {

        final MutableLiveData<List<ClsLayerItemMaster>> mutableLiveData = new MutableLiveData<>();

        InterfaceMain interfaceMain = ApiClient.getDemoInstance().create(InterfaceMain.class);
        Log.e("--Menu--", "interfaceDesignation: " + interfaceMain.toString());
        Call<List<ClsLayerItemMaster>> call = interfaceMain.GetMenuList(userCode);
        Log.e("--Menu--", "URL: " + call.request().url());

        call.enqueue(new Callback<List<ClsLayerItemMaster>>() {
            @Override
            public void onResponse(Call<List<ClsLayerItemMaster>> call, Response<List<ClsLayerItemMaster>> response) {
                Log.e("--Menu--", "response: " + response);
                if (response.body() != null) {
                    Log.e("--Menu--", "IF_response: " + response.body());
                    mutableLiveData.postValue(response.body());

                } else {
                    Log.e("--Menu--", "ElSE_response: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<ClsLayerItemMaster>> call, Throwable t) {
                mutableLiveData.postValue(null);
                Log.e("--Menu--", "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }

    private AppDatabase mAppDatabase;
    private AppExecutor mAppExecutor;

    public Repository(Context context) {
        // Create Instance of AppExecutor class.
        this.mAppExecutor = new AppExecutor();
        // Create Instance of AppDatebase class
        this.mAppDatabase = AppDatabase.getInstance(context);
        this.context = context;

        // Create All List Pets From Database and store in LiveData<List<PetsModel>> mPetsList var.


//        this.mProductList = mAppDatabase.getProductListDao().getPetsList();

    }


    public LiveData<ClsGetAppointmentResponse> getAppointment(ClsGetAppointment clsGetAppointment) {
        final MutableLiveData<ClsGetAppointmentResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetAppointment myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetAppointment.class);
        Call<ClsGetAppointmentResponse> call = myInterface.GetAppointment(clsGetAppointment);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsGetAppointmentResponse>() {
            @Override
            public void onResponse(Call<ClsGetAppointmentResponse> call, Response<ClsGetAppointmentResponse> response) {

                Log.e("--URL--", "onResponse - updateCustomerAddress :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----getAppointment---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsGetAppointmentResponse> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : updateCustomerAddress " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsCancelAppointment> cancelAppointment(ClsCancelAppointment clsCancelAppointment) {
        final MutableLiveData<ClsCancelAppointment> mutableLiveData = new MutableLiveData<>();
        InterfaceCancelAppoinment myInterface = ApiClient.getRetrofitInstance().create(InterfaceCancelAppoinment.class);
        Call<ClsCancelAppointment> call = myInterface.CancelAppointment(clsCancelAppointment);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsCancelAppointment>() {
            @Override
            public void onResponse(Call<ClsCancelAppointment> call, Response<ClsCancelAppointment> response) {

                Log.e("--URL--", "onResponse - cancelAppointment :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----cancelAppointment---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsCancelAppointment> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : cancelAppointment " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsMerchantProfileAdd> getMerchantProfileAdd(ClsMerchantProfileAdd clsMerchantProfileAdd) {
        final MutableLiveData<ClsMerchantProfileAdd> mutableLiveData = new MutableLiveData<>();
        InterfaceMerchantProfileAdd myInterface = ApiClient.getRetrofitInstance().create(InterfaceMerchantProfileAdd.class);
        Call<ClsMerchantProfileAdd> call = myInterface.MerchantProfileAdd(clsMerchantProfileAdd);

        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsMerchantProfileAdd>() {
            @Override
            public void onResponse(Call<ClsMerchantProfileAdd> call, Response<ClsMerchantProfileAdd> response) {

                Log.e("--URL--", "onResponse - cancelAppointment :" + response.code());
                if (response.body() != null && response.code() == 200) {

                    mutableLiveData.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----cancelAppointment---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsMerchantProfileAdd> call, Throwable t) {

                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure : cancelAppointment " + t.toString());
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsCategoriesResponse> getCategoriesList(String customerCode, String mode) {
        final MutableLiveData<ClsCategoriesResponse> cityResponseList = new MutableLiveData<>();
        InterfaceCategoriesList myInterface = ApiClient.getRetrofitInstance().create(InterfaceCategoriesList.class);
        Call<ClsCategoriesResponse> call = myInterface.GetCategoriesList(customerCode, mode);

        Log.e("--URL--", "************  before call : getCategoriesList" + call.request().url());

        call.enqueue(new Callback<ClsCategoriesResponse>() {
            @Override
            public void onResponse(Call<ClsCategoriesResponse> call, Response<ClsCategoriesResponse> response) {

                Log.e("--URL--", "onResponse: " + response.code());
                if (response.body() != null && response.code() == 200) {

                    cityResponseList.setValue(response.body());

                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(response.body());
                    Log.d("--URL--", "onResponse----getCategoriesList---: " + jsonInString);
                }
            }

            @Override
            public void onFailure(Call<ClsCategoriesResponse> call, Throwable t) {

                try {
                    cityResponseList.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:getCategoriesList " + t.toString());
            }
        });

        return cityResponseList;
    }

    @SuppressLint("StaticFieldLeak")
    public void InsertOrderDetail(ClsOrderDetailsEntity _objEntity) {

        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... voids) {
                return mAppDatabase.getOrderDetailDao().Insert(_objEntity);
            }

            @Override
            protected void onPostExecute(Long insertResult) {
                super.onPostExecute(insertResult);

                if (insertResult > 0) {
                    Toast.makeText(context, "Item added to cart.", Toast.LENGTH_LONG).show();
                    Log.e("--AddTO--", "IF: " + insertResult);
                } else {
                    Toast.makeText(context, "Failed to add Item.", Toast.LENGTH_LONG).show();
                    Log.e("--AddTO--", "ELSE: " + insertResult);
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void DeleteOrderByCode(String customerCode, String msg) {

        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected Integer doInBackground(Void... voids) {
                return mAppDatabase.getOrderDetailDao().DeleteOrderByCode(customerCode);
            }

            @Override
            protected void onPostExecute(Integer deleteResult) {
                super.onPostExecute(deleteResult);
                if (deleteResult > 0) {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Item deleted successfully!", Toast.LENGTH_LONG).show();

//                    Log.e("--size--", "Delete_IF: " + deleteResult);

                } else {
//                    Toast.makeText(context, "Failed to insert.", Toast.LENGTH_LONG).show();

                    Log.e("--size--", "Delete_ELSE: " + deleteResult);
                }

            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void DeleteByOrderDetailID(int OrderDetailID, String msg) {

        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Integer doInBackground(Void... voids) {
                return mAppDatabase.getOrderDetailDao().DeleteByOrderDetailID(OrderDetailID);
            }

            @Override
            protected void onPostExecute(Integer deleteResult) {
                super.onPostExecute(deleteResult);

                if (deleteResult > 0) {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_LONG).show();

//                    Log.e("--size--", "Delete_IF: " + deleteResult);

                } else {
                    Toast.makeText(context, "Order not placed!", Toast.LENGTH_LONG).show();

//                    Log.e("--size--", "Delete_ELSE: " + deleteResult);
                }

            }
        }.execute();
    }




    @SuppressLint("StaticFieldLeak")
    public List<ClsOrderDetailsEntity> getOrderDetailListByMerchantCode() {

        List<ClsOrderDetailsEntity> list = mAppDatabase.getOrderDetailDao()
                .getOrderDetailListByMerchantCode();
        list = sortAndAddSections(list);

        return list;
    }

    @SuppressLint("StaticFieldLeak")
    public void getItemNameByCharacter(String value) {

        new AsyncTask<Void, Void, LiveData<List<ClsOrderDetailsEntity>>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected LiveData<List<ClsOrderDetailsEntity>> doInBackground(Void... voids) {
                return mAppDatabase.getOrderDetailDao().getItemNameByCharacter(value);
            }

            @Override
            protected void onPostExecute(LiveData<List<ClsOrderDetailsEntity>> result) {
                super.onPostExecute(result);

            }
        }.execute();
    }

    public LiveData<List<ClsOrderDetailsEntity>> getOrderDetailList() {
        return mAppDatabase.getOrderDetailDao().getOrderDetailList();
    }


    public LiveData<List<ClsOrderDetailsEntity>> getOrderDetailByID(String _customerCode) {
        return mAppDatabase.getOrderDetailDao().getOrderDetailByID(_customerCode);
    }

    public LiveData<ClsCustomerOrderResponse> getCustomerOrderList(ClsCustomerOrderListParams clsCustomerOrderListParams) {
        final MutableLiveData<ClsCustomerOrderResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetOrderList myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetOrderList.class);

        Call<ClsCustomerOrderResponse> call = myInterface.getCustomerOrderListAPI(clsCustomerOrderListParams);

        Log.e("--URL--", "************  before call : " + call.request().url());
        call.enqueue(new Callback<ClsCustomerOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ClsCustomerOrderResponse> call,
                                   @NonNull Response<ClsCustomerOrderResponse> response) {

                mutableLiveData.postValue(response.body());

                Gson gson = new Gson();
                String jsonInString = gson.toJson(response.body());
                Log.e("--List--", "orders: " + jsonInString);

            }

            @Override
            public void onFailure(Call<ClsCustomerOrderResponse> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mutableLiveData;
    }

    private List<ClsOrderDetailsEntity> sortAndAddSections(List<ClsOrderDetailsEntity> itemList) {
        headerlist.clear();
        List<ClsOrderDetailsEntity> tempList = new ArrayList<>();
        String header = "";
        List<ClsTotalAmountCustomerCodeWise> totalAmountList =
                mAppDatabase.getOrderDetailDao().getTotalAmountCustomerCodeWise();

        Gson gson1 = new Gson();
        String jsonInString1 = gson1.toJson(totalAmountList);
        Log.e("--Test--", "totalAmountList: " + jsonInString1);

        for (int i = 0; i < itemList.size(); i++) {
            //If it is the start of a new section we create a new listcell and add it to our array

            if (itemList.get(i).getCustomerCode() != null) {
                //if (!itemList.get(i).isHeader()) {

                int finalI = i;
                OptionalDouble getTotalAmountByCustomerCode = StreamSupport.stream(totalAmountList)
                        .filter(s -> s.getCustomerCode().contains(itemList.get(finalI).getCustomerCode()))
                        .mapToDouble(ClsTotalAmountCustomerCodeWise::getTotalAmount)
                        .findAny();

                if (!(header.equals(String.valueOf(itemList.get(i).getCustomerCode())
                        .toUpperCase()))) {
                    ClsOrderDetailsEntity sectionCell = new ClsOrderDetailsEntity(
                            String.valueOf(itemList.get(i).getCustomerCode()).toUpperCase(),
                            itemList.get(i).getMerchantName());
                    sectionCell.setHeader(true);
                    sectionCell.setTotalAmountMerchantWise(getTotalAmountByCustomerCode.getAsDouble());
//                    sectionCell.setTotalAmountMerchantWise();
                    //A CHECK IN ALL ARRAY
                    if (!headerlist.contains(String.valueOf(itemList.get(i)
                            .getCustomerCode()).toUpperCase())) {

                        tempList.add(sectionCell);

                        headerlist.add(String.valueOf(itemList.get(i).getCustomerCode()).toUpperCase());
                    }
                }
                //}
            }

            Log.e("check", "outside if");
            tempList.add(itemList.get(i));
        }
        return tempList;
    }

    public LiveData<ClsGetMerchantPaymentMethodResponse> getMerchantPaymentMethod(String _merchantCode) {
        final MutableLiveData<ClsGetMerchantPaymentMethodResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetOrderList myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetOrderList.class);

        Call<ClsGetMerchantPaymentMethodResponse> call =
                myInterface.getMerchantPaymentMethod(_merchantCode);

        Log.e("--URL--", "************  before call : " + call.request().url());
        call.enqueue(new Callback<ClsGetMerchantPaymentMethodResponse>() {
            @Override
            public void onResponse(Call<ClsGetMerchantPaymentMethodResponse> call, Response<ClsGetMerchantPaymentMethodResponse> response) {
                mutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ClsGetMerchantPaymentMethodResponse> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mutableLiveData;
    }

    public LiveData<ClsOrderDetail> getFooterValue(String CustomerCode) {
        return mAppDatabase.getOrderDetailDao().getFooterValue(CustomerCode);
    }

    public MutableLiveData<ClsPlaceOrderDeleteResponse> cancelPlaceOrderAPI(ClsCancelOrderParams obj) {
        MutableLiveData<ClsPlaceOrderDeleteResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetOrderList mainInterface = ApiClient.getRetrofitInstance()
                .create(InterfaceGetOrderList.class);


        Call<ClsPlaceOrderDeleteResponse> call = mainInterface.deletePlaceOrder(obj);
        Log.e("--cancel--", "cancelPlaceOrderAPI: " + call.request().url());

        call.enqueue(new Callback<ClsPlaceOrderDeleteResponse>() {

            @Override
            public void onResponse(@NonNull Call<ClsPlaceOrderDeleteResponse> call,
                                   @NonNull Response<ClsPlaceOrderDeleteResponse> response) {


                if (response.body() != null && response.isSuccessful()) {

                    mutableLiveData.setValue(response.body());
                    Log.e("--cancel--", "IFIF: " + response.code());

                } else {
                    Log.e("--cancel--", "ELSE: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClsPlaceOrderDeleteResponse> call, @NonNull Throwable t) {
                mutableLiveData.setValue(null);
            }
        });


        return mutableLiveData;
    }


    public MutableLiveData<ClsFavoriteMerchantResponse> favoriteMerchantAPI(ClsFavoriteMerchantParams obj) {
        final MutableLiveData<ClsFavoriteMerchantResponse> mutableLiveData = new MutableLiveData<>();
        InterfaceGetOrderList myInterface = ApiClient.getRetrofitInstance().create(InterfaceGetOrderList.class);

        Call<ClsFavoriteMerchantResponse> call = myInterface.favoriteMerchant(obj);
        Log.e("--URL--", "************  before call : " + call.request().url());

        call.enqueue(new Callback<ClsFavoriteMerchantResponse>() {
            @Override
            public void onResponse(Call<ClsFavoriteMerchantResponse> call, Response<ClsFavoriteMerchantResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                    Log.e("--cancel--", "IFIF: " + response.code());
                } else {
                    Log.e("--cancel--", "ELSE: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ClsFavoriteMerchantResponse> call, Throwable t) {
                try {
                    mutableLiveData.postValue(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("--URL--", "onFailure:favoriteMerchant " + t.toString());
            }
        });
        return mutableLiveData;
    }
}
