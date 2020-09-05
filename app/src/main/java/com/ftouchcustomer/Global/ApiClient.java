package com.ftouchcustomer.Global;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static Retrofit retrofitTest;

    private static final String BASE_URL = "http://136.233.136.42:89/api/";
    private static final String ONLY_URL = "http://136.233.136.42:89/";

//     Live Api
//    private static final String BASE_URL = "https://www.ftouch.app:444/api/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeader())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getDemoInstance() {
        if (retrofitTest == null) {
            retrofitTest = new Retrofit.Builder()
                    .baseUrl(ONLY_URL)
//                    .baseUrl(TEST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeader())
                    .build();
        }
        return retrofitTest;
    }


    private static OkHttpClient getRequestHeader() {

        return new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new MyInterceptor())
                .retryOnConnectionFailure(true)
//                .connectionPool(new ConnectionPool(8, 120,
//                        TimeUnit.SECONDS))
                .build();
    }


}
