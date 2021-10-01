package com.example.countryflags.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static String BASE_URL = "https://api.printful.com/";
    public static String BASE_WEATHER_URL = "https://countrycode.org/";
    private static Retrofit retrofit;
    private static Retrofit weatherRetrofit;

    public static Retrofit getRetrofitClient(){
        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
    public static Retrofit getWeatherRetrofitClient(){
        if (weatherRetrofit ==null){
            weatherRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return weatherRetrofit;
    }
}
