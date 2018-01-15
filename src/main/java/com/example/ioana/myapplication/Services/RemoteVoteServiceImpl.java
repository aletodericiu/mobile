package com.example.ioana.myapplication.Services;

import com.example.ioana.myapplication.BuildConfig;
import com.example.ioana.myapplication.Domain.RecordVote;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Ioana on 1/13/2018.
 */

public class RemoteVoteServiceImpl {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.REMOTE_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static RemoteVoteServiceImpl.RemoteVoteServiceInterface service = null;

    public static RemoteVoteServiceImpl.RemoteVoteServiceInterface getInstance() {
        if (service == null) {
            service = retrofit.create(RemoteVoteServiceImpl.RemoteVoteServiceInterface.class);
        }
        return service;
    }

    public interface RemoteVoteServiceInterface  {

        @PUT("/vote/{id}.json")
        Call<RecordVote> createVote(
                @Path("id") String id,
                @Body RecordVote vote);

        @GET("/vote/{id}.json")
        Call<RecordVote> getVote(@Path("id") String id);

        @GET("/vote/.json")
        Call<Map<String, RecordVote>> getAllVotes();

    }
}