package com.example.countryflags.retrofit;

import com.example.countryflags.model.Result;
import com.example.countryflags.model.Weather;
import com.example.countryflags.model.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("countries")
    Call<data> getCountries();

    @GET("api/weather/conditions")
    Call<Weather> getWeather(@Query("country") String code);


}
