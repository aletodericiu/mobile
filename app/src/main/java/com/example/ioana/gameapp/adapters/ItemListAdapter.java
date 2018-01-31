package com.example.ioana.gameapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ioana.gameapp.R;
import com.example.ioana.gameapp.domain.Item;

import java.util.List;

/**
 * Created by Ioana on 1/30/2018.
 */

public class ItemListAdapter extends ArrayAdapter<Item> {

    private TextView mName;
    private TextView mQuantity;
    private TextView mType;
    private TextView mStatus;
    private TextView mId;

    private LinearLayout mLayoutStatus;

    private boolean mShouldShowStatus = true;

    public ItemListAdapter(Context context, List<Item> itemsList) {
        super(context, 0, itemsList);
    }

    public ItemListAdapter(Context context, List<Item> itemsList, boolean mShouldShowStatus) {
        super(context, 0, itemsList);
        this.mShouldShowStatus=mShouldShowStatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        Item item=getItem(position);

        if (listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item_view,parent,false);
        }

        mName=(TextView) listItemView.findViewById(R.id.name);
        mQuantity=(TextView) listItemView.findViewById(R.id.quantity);
        mType=(TextView) listItemView.findViewById(R.id.type);
        mStatus=(TextView) listItemView.findViewById(R.id.status);
        mId= (TextView) listItemView.findViewById(R.id.itemId);
        mLayoutStatus= (LinearLayout) listItemView.findViewById(R.id.layout_status);

        mName.setText(item.getName());
        mQuantity.setText(String.valueOf(item.getQuantity()));
        mType.setText(item.getType());
        mStatus.setText(item.getStatus());
        mId.setText(String.valueOf(item.getId()));

        if (!mShouldShowStatus) {
            mLayoutStatus.setVisibility(View.INVISIBLE);
        }
        return listItemView;
    }
}
