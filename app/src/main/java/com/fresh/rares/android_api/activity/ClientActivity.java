package com.fresh.rares.android_api.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fresh.rares.android_api.NotificationListenerThread;
import com.fresh.rares.android_api.R;
import com.fresh.rares.android_api.activity.register_logIn.MySignInActivity;
import com.fresh.rares.android_api.adapter.ClientList;
import com.fresh.rares.android_api.model.Client;
import com.fresh.rares.android_api.repository.APIRepositoryOnline;
import com.fresh.rares.android_api.repository.IRepository;
import com.fresh.rares.android_api.repository.InMemoryRepository;
import com.fresh.rares.android_api.repository.OperationRepository;
import com.fresh.rares.android_api.util.IOActions;
import com.fresh.rares.android_api.util.OperationUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Rares Abrudan on 1/18/2018.
 *
 * @email - raresabr@gmail.com
 */

public class ClientActivity extends BaseActivity
{
    //
    private EditText editTextField1;
    private EditText editTextField2;
    private EditText editTextField3;
    private EditText editTextField4;

    //
    Button addButton;
    Button deleteButton;
    Button updateButton;

    //
    public ListView listViewClients;

    //declare repositories
    private IRepository<Client> repositoryAPI;
    private IRepository<Client> repositoryInMemory;
    private OperationRepository repositoryOperation;

    //
    private IOActions ioActions;
    private OperationUtil operationUtil;

    //
    private String currentUser;
    //
    private Client currentClient;
    //
    private Boolean connectedToNetwork = true;
    private Boolean syncedWithOnline = false;

    //
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //
    private List<Client> clients = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //
        repositoryAPI = new APIRepositoryOnline(getApplicationContext());
        clients = repositoryAPI.getAll();

        repositoryInMemory = new InMemoryRepository();
        repositoryOperation = new OperationRepository();

        //
        ioActions = new IOActions(repositoryInMemory.getAll(), getApplicationContext());
        ioActions.readFromFile();

        //
//        operationUtil = new OperationUtil(repositoryOperation.getOperations(), getApplicationContext());
//        operationUtil.readFromFile();

        //
        initializeUIComponents();
        clickEventsRepository();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        addButton = findViewById(R.id.buttonAdd_ClientActivity);
        new Thread(() ->
        {
            //noinspection InfiniteLoopStatement
            while (true)
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressWarnings("ConstantConditions")
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                connectedToNetwork = networkInfo != null;

//                syncAndNetworkStatus();
//                if (connectedToNetwork && !syncedWithOnline && !operationUtil.checkEmptyFile())
//                {
//                    syncedWithOnline = true;
//                    syncRemoteDatabase();
//                }

                if (connectedToNetwork)
                {
                    addButton.postDelayed(() -> addButton.setVisibility(View.VISIBLE), 10);
                }
                else
                {
                    addButton.postDelayed(() -> addButton.setVisibility(View.INVISIBLE), 10);
                }


                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(() ->
        {
            while (true)
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if (connectedToNetwork)
                {
                    if (getWindow().getCurrentFocus() != null)
                    {
                        clients = repositoryAPI.getAll();
                        getWindow().getCurrentFocus().getRootView().postDelayed(this::refreshContent, 10);
                    }
                }
            }
        }).start();

        setAdapter();
        new NotificationListenerThread(6789, this.getBaseContext()).execute();
    }

    void setAdapter()
    {
        //creating adapter
        ClientList artistAdapter = new ClientList(ClientActivity.this, repositoryInMemory.getAll());
        //attaching adapter to the listView
        listViewClients.setAdapter(artistAdapter);
    }

    void initializeUIComponents()
    {
        //get the current user from the Login Activity
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("email");

        //
        listViewClients = findViewById(R.id.listViewClients_ClientActivity);

        //
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
    }

    void clickEventsRepository()
    {
        //add swipe refresh action
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshContent);

//        //add listener to view
//        listViewClients.setOnItemLongClickListener((adapterView, view, i, l) ->
//        {
//            //get client on position i
//            currentClient = (Client) listViewClients.getItemAtPosition(i);
//
//            //check if the current logged in client is an admin
//            if (!Objects.equals(currentClient.getEmail(), currentUser))
//            {
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", currentClient.getEmail(), null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "To: " + currentClient.getName() + "\nI want to rent: " + currentClient.getCarModel() + " " + currentClient.getCarName() + "\n");
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));
//            }
//            Toast.makeText(this, "Not allowed to send email", Toast.LENGTH_SHORT).show();
//            return false;
//        });

        //start activity to edit the client infos
        listViewClients.setOnItemClickListener((adapterView, view, position, l) ->
        {
            currentClient = (Client) listViewClients.getItemAtPosition(position);
//            if (Objects.equals(currentClient.getEmail(), currentUser) || Objects.equals(currentUser, "admin@gmail.com"))
//            {
            //inflate the layout for the update and delete dialog
            //inflate the layout for the update and delete dialog
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            @SuppressLint("InflateParams")
            View dialogView = layoutInflater.inflate(R.layout.layout_dialog_update_delete, null);

            //crate the dialog
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setView(dialogView);

            alertDialog.setTitle("Edit");

            //dialog box
            //declarations
            editTextField1 = dialogView.findViewById(R.id.editTextField1_dialogUpdateDelete_ClientActivity);
            editTextField2 = dialogView.findViewById(R.id.editTextField2_dialogUpdateDelete_ClientActivity);
            editTextField3 = dialogView.findViewById(R.id.editTextField3_dialogUpdateDelete_ClientActivity);
            editTextField4 = dialogView.findViewById(R.id.editTextField4_dialogUpdateDelete_ClientActivity);

            //fill the new dialog layout with existing data
            editTextField1.setText(currentClient.getField1());
            editTextField2.setText(currentClient.getField2());
            editTextField3.setText(currentClient.getField3());
            editTextField4.setText(currentClient.getField4());

            deleteButton = dialogView.findViewById(R.id.removeButton_dialogUpdateDelete_ClientActivity);
            updateButton = dialogView.findViewById(R.id.updateButton_dialogUpdateDelete_ClientActivity);

            deleteButton.setOnClickListener(v ->
            {
                repositoryInMemory.delete(currentClient);
                ioActions.writeToFile();
                setAdapter();

                if (connectedToNetwork)
                {
                    repositoryAPI.delete(currentClient);
                }
                else
                {
                    syncedWithOnline = false;

                    repositoryOperation.delete(currentClient);
//                    operationUtil.writeToFile();
                    System.out.println("OPERATIONS: " + repositoryOperation.getOperations());
                }
                alertDialog.dismiss();
            });

            updateButton.setOnClickListener(v ->
            {
                currentClient.setField1(editTextField1.getText().toString());
                currentClient.setField2(editTextField2.getText().toString());
                currentClient.setField3(editTextField3.getText().toString());
                currentClient.setField4(editTextField4.getText().toString());

                repositoryInMemory.update(currentClient, position);
                ioActions.writeToFile();
                setAdapter();

                if (connectedToNetwork)
                {
                    repositoryAPI.update(currentClient, position);
                }
                else
                {
                    syncedWithOnline = false;

                    repositoryOperation.update(currentClient, position);
//                    operationUtil.writeToFile();
                    System.out.println("OPERATIONS: " + repositoryOperation.getOperations());
                }
                alertDialog.dismiss();
            });

            alertDialog.show();

//            }
        });
    }

    private void refreshContent()
    {
        showProgressDialog();

        if (connectedToNetwork /*&& operationUtil.checkEmptyFile()*/)
        {
            repositoryInMemory.set(clients);
            ioActions.writeToFile();

            setAdapter();
        }
        else
        {
            Toast.makeText(this, "Unable to sync. Check internet connection!!", Toast.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout.setRefreshing(false);

        hideProgressDialog();
    }

    public void signOut(View view)
    {
        Intent intent = new Intent(this, MySignInActivity.class);
        intent.putExtra("logOut", 1);
        startActivity(intent);
    }

    /**
     * dialog box opens and lets you add client
     *
     * @param view -
     */
    public void addButton(View view)
    {
        //inflate the layout for the add dialog
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        View dialogView = layoutInflater.inflate(R.layout.layout_dialog_add, null);

        //crate the dialog
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(dialogView);

        alertDialog.setTitle("Add");

        editTextField1 = dialogView.findViewById(R.id.editTextField1_dialogAdd_ClientActivity);
        editTextField2 = dialogView.findViewById(R.id.editTextField2_dialogAdd_ClientActivity);
        editTextField3 = dialogView.findViewById(R.id.editTextField3_dialogAdd_ClientActivity);
        editTextField4 = dialogView.findViewById(R.id.editTextField4_dialogAdd_ClientActivity);

        addButton = dialogView.findViewById(R.id.addButton_dialogAdd_ClientActivity);
        addButton.setOnClickListener(v ->
        {
            @SuppressLint("SimpleDateFormat")
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Client client = new Client(timestamp, editTextField1.getText().toString(), editTextField2.getText().toString(),
                    editTextField3.getText().toString(), editTextField4.getText().toString());

            repositoryInMemory.add(client);
            ioActions.writeToFile();
            setAdapter();
            if (connectedToNetwork)
            {
                repositoryAPI.add(client);
            }
            else
            {
                syncedWithOnline = false;

                repositoryOperation.add(client);
//                operationUtil.writeToFile();
                System.out.println("OPERATIONS: " + repositoryOperation.getOperations());
            }
            alertDialog.dismiss();

        });
        alertDialog.show();
    }

    private void syncRemoteDatabase()
    {
        try
        {
            //open file
            FileInputStream fileInputStream = this.openFileInput("operations");

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
                String name = jsonArray.getJSONObject(i).getString("name");
                String email = jsonArray.getJSONObject(i).getString("email");
                String carModel = jsonArray.getJSONObject(i).getString("carModel");
                String carName = jsonArray.getJSONObject(i).getString("carName");

                Client client = new Client(id, name, email, carModel, carName);
                if (Objects.equals(operation, "add"))
                    repositoryAPI.add(client);
                else if (Objects.equals(operation, "remove"))
                    repositoryAPI.delete(client);
                else if (Objects.equals(operation, "update"))
                    repositoryAPI.update(client, 0);

                Thread.sleep(100);
            }

            FileOutputStream fileOutputStream = this.openFileOutput("operations", MODE_PRIVATE);
            fileOutputStream.write(("").getBytes());
            fileOutputStream.close();
        }
        catch (JSONException | IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
