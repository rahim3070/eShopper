package com.example.rahi.eshopper.utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getRetrofitClient(){
        return new Retrofit.Builder()
                .baseUrl("Your Url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
