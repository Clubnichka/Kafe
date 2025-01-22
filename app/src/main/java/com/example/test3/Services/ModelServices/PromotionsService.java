package com.example.test3.Services.ModelServices;

import android.util.Log;

import com.example.test3.Models.Promotion;
import com.example.test3.Models.SpecialOffer;
import com.example.test3.Services.ApiServices.ApiService;
import com.example.test3.Services.ApiServices.UnsafeOkHttpClient;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromotionsService {
    private List<Promotion> promos;
    private final OkHttpClient client = new UnsafeOkHttpClient().getUnsafeOkHttpClient();
    private static final String BASE_URL = "https://192.168.1.145:5005"; // Замените на ваш URL

    public PromotionsService(final PromosCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        fetchOffersData(apiService, callback);
    }






    private void fetchOffersData(ApiService apiService, final PromosCallback callback) {
        Call<List<Promotion>> call = apiService.getPromotions();
        call.enqueue(new Callback<List<Promotion>>() {
            @Override
            public void onResponse(Call<List<Promotion>> call, Response<List<Promotion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                     promos= response.body();
                    //Log.w("RespBody", response.body().getName());
                    if (callback != null) {
                        callback.onPromosLoaded(promos);
                    }
                } else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Promotion>> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }







    public interface PromosCallback {
        void onPromosLoaded(List<Promotion> promos);
    }



}
