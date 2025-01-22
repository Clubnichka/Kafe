package com.example.test3.Services.ModelServices;

import android.util.Log;

import com.example.test3.Models.Order;
import com.example.test3.Models.Product;
import com.example.test3.Services.ApiServices.ApiService;
import com.example.test3.Services.ApiServices.UnsafeOkHttpClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderService {
    private List<Product> products;
    ApiService apiService;
    Order[] orders;
    private final OkHttpClient client = new UnsafeOkHttpClient().getUnsafeOkHttpClient();
    private static final String BASE_URL = "https://192.168.1.145:5005"; // Замените на ваш URL

    public OrderService(List<Integer> ids, final ProductsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        fetchProductData(apiService,ids, callback);
    }

    public OrderService(int userid, final OrdersCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        getUserOrders(userid, callback);
    }

    public OrderService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public OrderService(emptycallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        callback.onLoaded();
    }

    private void fetchProductData(ApiService apiService,List<Integer> ids, final ProductsCallback callback) {
        Call<List<Product>> call = apiService.getProductsByIds(ids);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
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
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    public List<Product> getProducts() {
        return products != null ? products : new ArrayList<Product>() {};
    }

    public interface ProductsCallback {
        void onProductsLoaded(List<Product> products);
    }

    public interface OrdersCallback{
        void onOrdersLoaded(Order[] orders);
    }

    public interface OrderCallback{
        void onOrderLoaded(Order order);
    }

    public interface emptycallback{
        void onLoaded();
    }

    public void addOrder(List<Integer> ids,List<Integer> ids2, int userid){
        Call<String> call = apiService.addOrder(ids,ids2, userid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.w("addOrder", "Successfully added");
                }
             else

            {
                Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
            }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    public void addOrder(List<Integer> ids, int userid, int type){
        Call<String> call = apiService.addOrder(ids, userid, type);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.w("addOrder", "Successfully added");
                }
                else

                {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

//    public void addOrder(List<Integer> ids, int userid){
//        Call<String> call = apiService.addOrder(ids, userid);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    Log.w("addOrder", "Successfully added");
//                }
//                else
//
//                {
//                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.e("MainActivity", "Ошибка: " + t.getMessage());
//            }
//        });
//    }

    public void getUserOrders(int userid, final OrdersCallback callback){
        Call<Order[]> call = apiService.getUserOrders(userid);
        call.enqueue(new Callback<Order[]>() {
            @Override
            public void onResponse(Call<Order[]> call, Response<Order[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders=response.body();
                    if (callback != null) {
                        callback.onOrdersLoaded(orders);
                    }
                    Log.w("addOrder", "Successfully added");
                }
                else

                {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }

            }

            @Override
            public void onFailure(Call<Order[]> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    public void getCurrentOrder(int userid, OrderCallback callback){
        Call<Order> call=apiService.getCurrentOrder(userid);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order order=response.body();
                    if (callback != null) {
                        callback.onOrderLoaded(order);
                    }
                    Log.w("addOrder", "Successfully added");
                }
                else

                {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
}
