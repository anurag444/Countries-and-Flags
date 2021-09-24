package com.example.countryflags.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countryflags.model.Result;
import com.example.countryflags.model.data;
import com.example.countryflags.retrofit.Api;
import com.example.countryflags.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryViewModel extends ViewModel {

    private MutableLiveData<List<Result>> countriesList;

    public CountryViewModel(){
        countriesList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Result>> getCountriesList(){
        return countriesList;
    }

    public void makeApiCall(){
        Api api = RetrofitClient.getRetrofitClient().create(Api.class);
        Call<data> call= api.getCountries();
        call.enqueue(new Callback<data>() {
            @Override
            public void onResponse(Call<data> call, Response<data> response) {
                if (response.body() != null){
                    countriesList.postValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<data> call, Throwable t) {
                countriesList.postValue(null);
            }
        });
    }
}
