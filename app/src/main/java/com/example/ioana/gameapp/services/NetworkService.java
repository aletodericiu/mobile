package com.example.ioana.gameapp.services;

import com.example.ioana.gameapp.domain.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ale on 31/01/2018.
 */

public class NetworkService {

    public static final String BASE_URL = "http://192.168.0.117:4001";

    private static Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static NetworkServiceInterface mInstance = null;

    public static NetworkServiceInterface getInstance() {
        if (mInstance == null) {
            mInstance = mRetrofit.create(NetworkServiceInterface.class);
        }
        return mInstance;
    }

    public interface NetworkServiceInterface {
        @GET("/all")
        Call<List<Item>> getAllGames();

        @POST("/addGame")
        Call<Item> addItem(@Body Item item);

        @POST("/removeGame")
        Call<Item> removeItem(@Body Item item);

        @POST("/updateGame")
        Call<Item> updateItem(@Body Item item);

        @GET("/games")
        Call<List<Item>> getGamesClient();
    }
}