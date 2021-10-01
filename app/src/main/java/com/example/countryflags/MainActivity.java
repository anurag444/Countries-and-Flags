package com.example.countryflags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.countryflags.adapter.CountryAdapter;
import com.example.countryflags.model.Result;
import com.example.countryflags.model.Weather;
import com.example.countryflags.model.data;
import com.example.countryflags.retrofit.Api;
import com.example.countryflags.retrofit.RetrofitClient;
import com.example.countryflags.viewmodel.CountryViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView country;
    private ImageView countryFlag;
    private LinearLayout countryLayout;
    private List<Result> countryList = new ArrayList<>();
    private CountryAdapter adapter;
    private CountryViewModel viewModel;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView temperature, minMax, weatherType;
    private ImageView weatherImage;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setDefaultValues();

        countryLayout.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(R.layout.country_bottom_sheet);
            dialog.setCancelable(false);
            dialog.show();

            RecyclerView recyclerView = dialog.findViewById(R.id.country_list);
            ProgressBar progressBar = dialog.findViewById(R.id.progress);
            Button done = dialog.findViewById(R.id.apply);

            try {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                adapter = new CountryAdapter(this, countryList);
                recyclerView.setAdapter(adapter);

                viewModel.getCountriesList().observe(this, new Observer<List<Result>>() {
                    @Override
                    public void onChanged(List<Result> results) {
                        if (results != null) {
                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            countryList = results;
                            adapter.setCountries(results);
                        }
                    }
                });
                viewModel.makeApiCall();

                done.setOnClickListener(v1 -> {
                    if (adapter.getSelected() != null) {
                        editor.putString("country_name", adapter.getSelected().getName());
                        editor.putString("country_code", adapter.getSelected().getCode());
                        editor.apply();
                        country.setText(adapter.getSelected().getName());
                        setImage(adapter.getSelected().getCode());
                        getWeather(adapter.getSelected().getCode());
                    }

                    dialog.dismiss();
                });
            }catch (Exception e){
                Log.e("MainActivity", e.getMessage());
            }
        });
    }

    private void initViews() {
        country = findViewById(R.id.countryName);
        countryFlag = findViewById(R.id.countryFlag);
        countryLayout = findViewById(R.id.ll);
        weatherImage = findViewById(R.id.weather_image);
        weatherType = findViewById(R.id.weather_type);
        temperature = findViewById(R.id.temp);
        minMax = findViewById(R.id.min_max);
        preferences = getSharedPreferences("countryName", Context.MODE_PRIVATE);
        editor = preferences.edit();
        viewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
    }

    private void setDefaultValues() {
        String code = "in";
        if (!preferences.getString("country_name", "").equals("")) {
            country.setText(preferences.getString("country_name", ""));
        }
        if (!preferences.getString("country_code", "").equals("")) {
            code = preferences.getString("country_code", "");
        }
        setImage(code);
        getWeather(code);
    }

    private void setImage(String code) {
        RequestOptions requestOption = new RequestOptions().placeholder(R.drawable.loading).centerCrop();
        Glide.with(countryFlag).load("https://www.countryflags.io/" + code + "/flat/64.png").apply(requestOption).centerCrop().into(countryFlag);
    }

    private void getWeather(String code){
        Api api = RetrofitClient.getWeatherRetrofitClient().create(Api.class);
        Call<Weather> call= api.getWeather(code);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.body() != null){
                    //Update data
                    Weather weather = response.body();
                    String temp = weather.getTemp() + "\u00B0" +weather.getTempUnits();
                    temperature.setText(temp);
                    weatherType.setText(weather.getConditions().toUpperCase(Locale.ROOT));
                    Glide.with(MainActivity.this).load(weather.getImageUrl()).into(weatherImage);
                    String minMaxTemp = weather.getLow() + "\u00B0" + weather.getTempUnits() + "/" + weather.getHigh() + "\u00B0" + weather.getTempUnits();
                    minMax.setText(minMaxTemp);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
            }
        });
    }

}