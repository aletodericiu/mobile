package com.fresh.rares.android_api.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fresh.rares.android_api.model.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rares Abrudan on 1/18/2018.
 *
 * @email - raresabr@gmail.com
 */

public class APIRepositoryOnline implements IRepository<Client>
{
    private List<Client> clients;
    private RequestQueue requestQueue;

    public APIRepositoryOnline(Context context)
    {
        clients = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void add(Client client)
    {
        // Optional Parameters to pass as POST request
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("uuid", client.getId());
            jsonObject.put("name", client.getField1());
            jsonObject.put("email", client.getField2());
            jsonObject.put("carModel", client.getField3());
            jsonObject.put("carName", client.getField4());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        final String url = "http://10.0.2.2:8081/client/create/";
        // Make request for JSONObject
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response ->
                {
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
    public void delete(Client client)
    {
        final String url = "http://10.0.2.2:8081/client/delete/" + client.getId();
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
    public void update(Client client, int position)
    {
        // Optional Parameters to pass as POST request
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("uuid", client.getId());
            jsonObject.put("name", client.getField1());
            jsonObject.put("email", client.getField2());
            jsonObject.put("carModel", client.getField3());
            jsonObject.put("carName", client.getField4());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        final String url = "http://10.0.2.2:8081/client/update/";
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
    public List<Client> getAll()
    {
        try
        {
            clients.clear();
            getClientsFromAPI();
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void set(List<Client> clients)
    {

    }

    private void getClientsFromAPI()
    {
        new Thread(() ->
        {
            final String url = "http://10.0.2.2:8081/client/";
            // prepare the Request
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response ->
                    {
                        for (int i = 0; i < response.length(); i++)
                            try
                            {
                                clients.add(new Client(response.getJSONObject(i).getString("uuid"), response.getJSONObject(i).getString("name"), response.getJSONObject(i).getString("email"),
                                        response.getJSONObject(i).getString("carModel"), response.getJSONObject(i).getString("carName")));
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
