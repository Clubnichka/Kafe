package com.example.test3.Services.ModelServices;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.test3.Models.User;
import com.example.test3.Services.ApiServices.ApiService;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ApiServices.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private User user;
    private boolean result;
    private final OkHttpClient client = new UnsafeOkHttpClient().getUnsafeOkHttpClient();
    private static final String BASE_URL = "https://192.168.1.145:5005"; // Замените на ваш URL
    private ApiService apiService;
    public UserService(final UserCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        fetchUserData(apiService, callback);
    }

    private void fetchUserData(ApiService apiService, final UserCallback callback) {
        Call<User> call = apiService.getUserData();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user = response.body();
                    Log.w("RespBody", response.body().getName());
                    if (callback != null) {
                        callback.onUserLoaded(user);
                    }
                } else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
    }

    public User getUser() {
        return user != null ? user : new User();
    }

    public interface UserCallback {
        void onUserLoaded(User user);
    }

    public void getLevel (int userid, DBHelper dbHelper){
        Call<Integer> call = apiService.getLevel(userid);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() != null) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("Level", response.body());
                        String selection = "userid = ?"; // Условие, по которому будет выполнено обновление
                        String[] selectionArgs = { String.valueOf(userid) };
                        db.update("User", values, selection, selectionArgs);
                        result = true;
                    } else {
                        result = false;
                    }
                }
                else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.raw());

                }
            }


            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });

    }

    public boolean enter (String name, String password, DBHelper dbHelper){
        Log.w("User Service userpass",name+" "+password);
        Call<User> call = apiService.enter(name,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getName()!=null) {
                        Log.w("User Service name from service", response.body().getName());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("Level", response.body().getLevel());
                        values.put("userid",response.body().getId());
                        values.put("Name", response.body().getName()); // Замените "product_id" на имя вашего столбца
                        db.insert("User", null, values);
                        result = true;
                    } else {
                        Log.w("User Service", "Вернулось nea"+response.body().getName());
                        result = false;
                    }
                }
                else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.raw());

                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
        return result;
    }

    public boolean register (User user, DBHelper dbHelper){
        Call<User> call = apiService.register(user.getName(),user.getPassword());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() != null) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("Level", response.body().getLevel());
                        values.put("userid",response.body().getId());
                        values.put("Name", response.body().getName()); // Замените "product_id" на имя вашего столбца
                        db.insert("User", null, values);
                        result = true;
                    } else {
                        result = false;
                    }
                }
                else {
                    Log.e("MainActivity", "Ошибка получения данных: " + response.code() + " - " + response.raw());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("MainActivity", "Ошибка: " + t.getMessage());
            }
        });
        return result;
    }
}