package com.fresh.rares.android_api.util;

import android.content.Context;

import com.fresh.rares.android_api.model.Client;
import com.fresh.rares.android_api.model.Operation;

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

public class OperationUtil
{
    private final Context context;
    private List<Operation> operationsList;

    public OperationUtil(List<Operation> operationsList, Context context)
    {
        this.operationsList = operationsList;
        this.context = context;
    }

    public void readFromFile()
    {
        try
        {
            //open file
            FileInputStream fileInputStream = context.openFileInput("operations");

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
                String operation = jsonArray.getJSONObject(i).getString("operation");
                String id = jsonArray.getJSONObject(i).getString("id");
                String name = jsonArray.getJSONObject(i).getString("field1");
                String email = jsonArray.getJSONObject(i).getString("field2");
                String carModel = jsonArray.getJSONObject(i).getString("field3");
                String carName = jsonArray.getJSONObject(i).getString("field4");

                Client client = new Client(id, name, email, carModel, carName);
                operationsList.add(new Operation(operation, client));
            }
        }
        catch (JSONException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToFile()
    {
        try
        {
            JSONArray jsonArray = new JSONArray();
            JSONObject person;

            for (int i = 0; i < operationsList.size(); i++)
            {
                person = new JSONObject();

                person.put("operation", operationsList.get(i).getOperation());
                person.put("id", operationsList.get(i).getClient().getId());
                person.put("field1", operationsList.get(i).getClient().getField1());
                person.put("field2", operationsList.get(i).getClient().getField2());
                person.put("field3", operationsList.get(i).getClient().getField3());
                person.put("field4", operationsList.get(i).getClient().getField4());

                jsonArray.put(person);
            }

            String textFile = jsonArray.toString();

            FileOutputStream fileOutputStream = context.openFileOutput("operations", MODE_PRIVATE);

            fileOutputStream.write(textFile.getBytes());
            fileOutputStream.close();
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    public Boolean checkEmptyFile()
    {
        try
        {
            //open file
            FileInputStream fileInputStream;
            fileInputStream = context.openFileInput("operations");

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
