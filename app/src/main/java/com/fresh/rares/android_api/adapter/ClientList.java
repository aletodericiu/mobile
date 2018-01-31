package com.fresh.rares.android_api.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fresh.rares.android_api.R;
import com.fresh.rares.android_api.model.Client;

import java.util.List;

/**
 * Created by Rares Abrudan on 1/18/2018.
 *
 * @email - raresabr@gmail.com
 */

public class ClientList extends ArrayAdapter<Client>
{
    private Activity activityContext;
    private List<Client> clients;

    public ClientList(Activity activityContext, List<Client> clients)
    {
        super(activityContext, R.layout.layout_client_list, clients);
        this.activityContext = activityContext;
        this.clients = clients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //setup inflater
        LayoutInflater inflater = activityContext.getLayoutInflater();

        //inflate the new layout
        @SuppressLint({"ViewHolder", "InflateParams"})
        View listViewItem = inflater.inflate(R.layout.layout_client_list, null, true);

        //initialize the textViews
        TextView textViewField1 = listViewItem.findViewById(R.id.textViewField1_ClientList);
        TextView textViewField2 = listViewItem.findViewById(R.id.textViewField2_ClientList);
        TextView textViewField3 = listViewItem.findViewById(R.id.textViewField3_ClientList);
        TextView textViewField4 = listViewItem.findViewById(R.id.textViewField4_ClientList);


        //get the client from the given position
        Client client = clients.get(position);

        //set all textViews with values
        textViewField1.setText(client.getField1());
        textViewField2.setText(client.getField2());
        textViewField3.setText(client.getField3());
        textViewField4.setText(client.getField4());

        return listViewItem;
    }
}
