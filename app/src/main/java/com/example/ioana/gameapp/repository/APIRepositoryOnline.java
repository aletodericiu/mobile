package com.example.ioana.gameapp.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ioana.gameapp.domain.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioana on 1/31/2018.
 */

public class APIRepositoryOnline implements IRepository<Item> {

    private List<Item> items;
    private RequestQueue requestQueue;

    public APIRepositoryOnline(Context context)
    {
        items = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void add(Item item) {

        // Optional Parameters to pass as POST request
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("id", item.getId());
            jsonObject.put("name", item.getName());
            jsonObject.put("quantity", item.getQuantity());
            jsonObject.put("type", item.getType());
            jsonObject.put("status", item.getStatus());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        final String url = "http://10.152.3.16:4001/addGame/";
        // Make request for JSONObject
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try
                    {
                        System.out.println(response.getString("ip"));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                error -> System.out.println(error.getMessage() + " " + error.toString()));
        // Adding request to request queue
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void delete(Item item) {
        final String url = "http://10.152.3.16:4001/removeGame/" + item.getId();
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                response ->
                {
                    System.out.println("Deletion Successful" + response);
                },
                error ->
                        System.out.println("Deletion Error Encountered" + error.getMessage())
        );
        requestQueue.add(deleteRequest);
    }

    @Override
    public void update(Item item, int position) {
        // Optional Parameters to pass as POST request
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("id", item.getId());
            jsonObject.put("name", item.getName());
            jsonObject.put("quantity", item.getQuantity());
            jsonObject.put("type", item.getType());
            jsonObject.put("status", item.getStatus());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        final String url = "http://10.152.3.16:4001/updateGame/";
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                response
                        -> System.out.println("Update Success: "),
                error
                        -> System.out.println("Update Error: "));

        // Adding request to request queue
        requestQueue.add(jsonObjReq);

    }

    @Override
    public List<Item> getAll() {
        try
        {
            items.clear();
            getItemsFromAPI();
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void set(List<Item> items) {

    }
    private void getItemsFromAPI()
    {
        new Thread(() ->
        {
            final String url = "http://10.152.3.16:4001/all/";
            // prepare the Request
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response ->
                    {
                        for (int i = 0; i < response.length(); i++)
                            try
                            {
                                items.add(new Item(response.getJSONObject(i).getInt("id"), response.getJSONObject(i).getString("name"), response.getJSONObject(i).getInt("quantity"),
                                        response.getJSONObject(i).getString("type"), response.getJSONObject(i).getString("status")));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                    },
                    error ->
                            System.out.println("GET Error: ")
            );
            // add it to the RequestQueue
            requestQueue.add(getRequest);
        }).start();
    }
}
