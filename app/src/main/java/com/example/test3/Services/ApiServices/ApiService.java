package com.example.test3.Services.ApiServices;

import com.example.test3.Models.Order;
import com.example.test3.Models.Product;
import com.example.test3.Models.Promotion;
import com.example.test3.Models.SpecialOffer;
import com.example.test3.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/home/") // Убедитесь, что путь соответствует вашему контроллеру
    Call<User> getUserData();

    @GET("api/home/getProducts")
    Call<Product[]> getProductsData();

    @GET("api/home/getProductsByIds")
    Call<List<Product>> getProductsByIds(@Query("ids") List<Integer> ids);

    @POST("api/home/addOrder")
    Call<String> addOrder(@Query("ids") List<Integer> ids,@Query("ids2")List<Integer> ids2, @Query("userid") int userid);
    @POST("api/home/addOrder1")
    Call<String> addOrder(@Query("ids") List<Integer> ids, @Query("userid") int userid, @Query("type") int type);


//    @POST("api/home/addOrder1")
//    Call<String> addOrder(@Query("ids") List<Integer> ids, @Query("userid") int userid);

    @GET("api/home/enter")
    Call<User> enter(@Query("name") String name, @Query("password") String password);

    @GET("api/home/register")
    Call<User> register(@Query("name") String name, @Query("password") String password);

    @GET("api/home/getUserOrders")
    Call<Order[]> getUserOrders(@Query("userid") int userid);

    @GET("api/home/getProductsByOrderId")
    Call<Product[]> getProductsByOrderId(@Query("orderid") int orderid);

    @GET("api/home/getSpecialOffers")
    Call<SpecialOffer[]> getSpecialOffers(@Query("userid") int userid);

    @GET("api/home/getSpecialOffersByIds")
    Call<SpecialOffer[]> getSpecialOffersByIds(@Query("ids")List<Integer> ids);

    @GET("api/home/getOffersByOrderId")
    Call<SpecialOffer[]> getOffersByOrderId(@Query("orderid")int orderid);

    @GET("api/home/getLevel")
    Call<Integer> getLevel(@Query("userid") int userid);

    @GET("api/home/getPromotions")
    Call<List<Promotion>> getPromotions();

    @GET("api/home/getCurrentOrder")
    Call<Order> getCurrentOrder(@Query("userid") int userid);
}