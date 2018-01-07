package com.example.ioana.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ioana.myapplication.Domain.RecordItem;

import java.util.List;

/**
 * Created by Ioana on 11/11/2017.
 */

public class RecordListAdapter extends ArrayAdapter<RecordItem> {

    public RecordListAdapter(Context context, List<RecordItem> recordsList) {
        super(context, 0, recordsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if (listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item_view,parent,false);
        }
        RecordItem record=getItem(position);
        TextView song_view=(TextView) listItemView.findViewById(R.id.song);
        TextView band_view=(TextView) listItemView.findViewById(R.id.band);
        TextView genre_view=(TextView) listItemView.findViewById(R.id.genre);
        TextView id_view= (TextView) listItemView.findViewById(R.id.recordId);
        song_view.setText(record.getName());
        band_view.setText(record.getBand());
        genre_view.setText(record.getGenre());
        id_view.setText(record.getId().toString());
        listItemView.setOnLongClickListener(new OnLongClickListenerRecord());

        return listItemView;
    }

}