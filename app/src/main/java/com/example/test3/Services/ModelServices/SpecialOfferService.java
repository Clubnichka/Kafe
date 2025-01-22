package com.example.test3.Services.ModelServices;

import android.util.Log;

import com.example.test3.Models.Product;
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

public class SpecialOfferService {
    private SpecialOffer[] offers;
    private final OkHttpClient client = new UnsafeOkHttpClient().getUnsafeOkHttpClient();
    private static final String BASE_URL = "https://192.168.1.145:5005"; // Замените на ваш URL

    public SpecialOfferService(int userid, final OffersCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        fetchOffersData(apiService, userid, callback);
    }

    public SpecialOfferService(List<Integer> ids, final OffersCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        fetchOffersData(apiService,ids, callback);
    }

    public SpecialOfferService(int orderid,boolean overload, final OffersCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        getOffersByOrderId(orderid,apiService, callback);
    }

    private void fetchOffersData(ApiService apiService,int userid, final OffersCallback callback) {
        Call<SpecialOffer[]> call = apiService.getSpecialOffers(userid);
        call.enqueue(new Callback<SpecialOffer[]>() {
            @Override
            public void onResponse(Call<SpecialOffer[]> call, Response<SpecialOffer[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offers = response.body();
                    //Log.w("RespBody", response.body().getName());
                    if (callback != null) {
                        callback.onOffersLoaded(offers);
                    }
                } else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SpecialOffer[]> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    private void fetchOffersData(ApiService apiService, List<Integer> ids, final OffersCallback callback) {
        Call<SpecialOffer[]> call = apiService.getSpecialOffersByIds(ids);
        call.enqueue(new Callback<SpecialOffer[]>() {
            @Override
            public void onResponse(Call<SpecialOffer[]> call, Response<SpecialOffer[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offers = response.body();
                    //Log.w("RespBody", response.body().getName());
                    if (callback != null) {
                        callback.onOffersLoaded(offers);
                    }
                } else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SpecialOffer[]> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    public void getOffersByOrderId(int orderid, ApiService apiService, final OffersCallback callback){
        Call<SpecialOffer[]> call=apiService.getOffersByOrderId(orderid);
        call.enqueue(new Callback<SpecialOffer[]>() {
            @Override
            public void onResponse(Call<SpecialOffer[]> call, Response<SpecialOffer[]> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    offers= response.body();;
                    if(callback!=null){
                        callback.onOffersLoaded(offers);
                    }
                }
            }

            @Override
            public void onFailure(Call<SpecialOffer[]> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }



    public interface OffersCallback {
        void onOffersLoaded(SpecialOffer[] offers);
    }

//    public void getProductsByOrderId(int orderid, ApiService apiService, final ProductsCallback callback){
//        Call<Product[]> call=apiService.getProductsByOrderId(orderid);
//        call.enqueue(new Callback<Product[]>() {
//            @Override
//            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
//                if (response.isSuccessful()&&response.body()!=null){
//                    products= response.body();;
//                    if(callback!=null){
//                        callback.onProductsLoaded(products);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product[]> call, Throwable t) {
//                Log.e("MainActivity", "Ошибка: " + t.getMessage());
//            }
//        });
//    }
}
