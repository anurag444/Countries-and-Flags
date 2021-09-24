package com.example.countryflags.retrofit;

import com.example.countryflags.model.Result;
import com.example.countryflags.model.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("countries")
    Call<data> getCountries();
}
