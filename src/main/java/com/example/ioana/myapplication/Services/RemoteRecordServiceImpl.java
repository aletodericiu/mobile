package com.example.ioana.myapplication.Services;

import com.example.ioana.myapplication.BuildConfig;
import com.example.ioana.myapplication.Domain.RecordItem;
import com.example.ioana.myapplication.Observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Ioana on 1/13/2018.
 */

public class RemoteRecordServiceImpl {
        private static Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.REMOTE_SERVICE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // use gson converter
        .build();

        private static RemoteRecordServiceInterface service = null;

        public static RemoteRecordServiceInterface  getInstance() {
            if (service == null) {
                service = retrofit.create(RemoteRecordServiceInterface.class);
            }
            return service;
        }


        private static List<Observer> observers = new ArrayList<Observer>();
        public static void attach(Observer observer){
            observers.add(observer);
        }

        public static void notifyAllObservers() {
            for (Observer observer : observers) {
                observer.update();
            }
        }

public interface RemoteRecordServiceInterface  {

    @PUT("/song/{name}.json")
    Call<RecordItem> createRecord(
            @Path("name") String name,
            @Body RecordItem record);

    @GET("/song/{name}.json")
    Call<RecordItem> getRecord(@Path("name") String name);

    @GET("/song/.json")
    Call<Map<String, RecordItem>> getAllRecords();

    @DELETE("/song/{name}.json")
    Call<RecordItem> deleteRecord(@Path("name") String name);

}
}