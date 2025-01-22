package com.example.test3.Services.ModelServices;

import android.util.Log;

import com.example.test3.Models.Product;
import com.example.test3.Services.ApiServices.ApiService;
import com.example.test3.Services.ApiServices.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductService {
    private Product[] products;
    private final OkHttpClient client = new UnsafeOkHttpClient().getUnsafeOkHttpClient();
    private static final String BASE_URL = "https://192.168.1.145:5005"; // Замените на ваш URL

    public ProductService(final ProductsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        fetchProductData(apiService, callback);
    }

    public ProductService(int orderid, final ProductsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        getProductsByOrderId(orderid,apiService, callback);
    }

    private void fetchProductData(ApiService apiService, final ProductsCallback callback) {
        Call<Product[]> call = apiService.getProductsData();
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products = response.body();
                    //Log.w("RespBody", response.body().getName());
                    if (callback != null) {
                        callback.onProductsLoaded(products);
                    }
                } else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    public Product[] getProducts() {
        return products != null ? products : new Product[]{};
    }

    public interface ProductsCallback {
        void onProductsLoaded(Product[] products);
    }

    public void getProductsByOrderId(int orderid, ApiService apiService, final ProductsCallback callback){
        Call<Product[]> call=apiService.getProductsByOrderId(orderid);
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    products= response.body();;
                    if(callback!=null){
                        callback.onProductsLoaded(products);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }
}
