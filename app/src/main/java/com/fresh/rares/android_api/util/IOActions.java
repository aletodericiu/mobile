package com.fresh.rares.android_api.util;

import android.content.Context;

import com.fresh.rares.android_api.model.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rares Abrudan on 1/16/2018.
 *
 * @email - raresabr@gmail.com
 */

public class IOActions
{
    private List<Client> clients;
    private Context context;

    public IOActions(List<Client> clients, Context context)
    {
        this.clients = clients;
        this.context = context;
    }

    public void writeToFile()
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            JSONObject person;

            for (int i = 0; i < clients.size(); i++)
            {
                person = new JSONObject();

                person.put("id", clients.get(i).getId());
                person.put("field1", clients.get(i).getField1());
                person.put("field2", clients.get(i).getField2());
                person.put("field3", clients.get(i).getField3());
                person.put("field4", clients.get(i).getField4());

                jsonArray.put(person);
            }

            String textFile = jsonArray.toString();

            FileOutputStream fileOutputStream = context.openFileOutput("records", MODE_PRIVATE);

            fileOutputStream.write(textFile.getBytes());
            fileOutputStream.close();
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void readFromFile()
    {
        try
        {
            //open file
            FileInputStream fileInputStream = context.openFileInput("records");

            //make file readable
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedInputStream.available() != 0)
            {
                char character = (char) bufferedInputStream.read();
                stringBuilder.append(character);
            }
            bufferedInputStream.close();
            fileInputStream.close();


            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++)
            {
                String id = jsonArray.getJSONObject(i).getString("id");
                String name = jsonArray.getJSONObject(i).getString("field1");
                String email = jsonArray.getJSONObject(i).getString("field2");
                String carModel = jsonArray.getJSONObject(i).getString("field3");
                String carName = jsonArray.getJSONObject(i).getString("field4");

                Client client = new Client(id, name, email, carModel, carName);
                clients.add(client);
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String readLoggedInUserFromFile()
    {
        try
        {
            //if the file is empty -> no user is logged in
//            if (checkEmptyFile("loginsession")) return null;

            //open file
            FileInputStream fileInputStream = context.openFileInput("loginsession");

            //make file readable
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedInputStream.available() != 0)
            {
                char character = (char) bufferedInputStream.read();
                stringBuilder.append(character);
            }
            bufferedInputStream.close();
            fileInputStream.close();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            return jsonArray.getJSONObject(0).getString("username");
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void writeLoggedInUserToFile(String username)
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            JSONObject person;

            person = new JSONObject();
            person.put("username", username);
            jsonArray.put(person);

            String textFile = jsonArray.toString();

            FileOutputStream fileOutputStream = context.openFileOutput("loginsession", MODE_PRIVATE);

            fileOutputStream.write(textFile.getBytes());
            fileOutputStream.close();
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void emptyFile()
    {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput("loginsession", MODE_PRIVATE);
            fileOutputStream.write(("").getBytes());
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Boolean checkEmptyFile(String name)
    {
        try
        {
            //open file
            FileInputStream fileInputStream;
            fileInputStream = context.openFileInput(name);

            //make file readable
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            if (bufferedInputStream.available() != 0)
            {
                return false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
