package com.example.ioana.gameapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ioana.gameapp.EditItemActivity;
import com.example.ioana.gameapp.R;
import com.example.ioana.gameapp.components.GsonComponent;
import com.example.ioana.gameapp.domain.Item;
import android.content.Intent;

import java.util.List;

/**
 * Created by Ioana on 1/30/2018.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    public static final String TAG=ItemListAdapter.class.getSimpleName();
    Context mContext;

    private List<Item> mListData;

    private LinearLayout mLayoutStatus;

    private boolean mIsEmployeeAdapter = true;

    public ItemListAdapter(Context context){
        mContext=context;
    }

    public ItemListAdapter(Context context,boolean mIsEmployeeAdapter){
        mContext=context;
        this.mIsEmployeeAdapter=mIsEmployeeAdapter;
    }


    public class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mName;
        private TextView mQuantity;
        private TextView mType;
        private TextView mStatus;
        private TextView mId;

        public ItemListViewHolder(View listItemView) {
            super(listItemView);

            mName=(TextView) listItemView.findViewById(R.id.name);
            mQuantity=(TextView) listItemView.findViewById(R.id.quantity);
            mType=(TextView) listItemView.findViewById(R.id.type);
            mStatus=(TextView) listItemView.findViewById(R.id.status);
            mId= (TextView) listItemView.findViewById(R.id.itemId);
            mLayoutStatus= (LinearLayout) listItemView.findViewById(R.id.layout_status);

            if(!mIsEmployeeAdapter){
                mLayoutStatus.setVisibility(View.INVISIBLE);
            }else {
                listItemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Item item = mListData.get(adapterPosition);

            Intent intent = new Intent(mContext, EditItemActivity.class);
            intent.putExtra(Item.class.getSimpleName(), GsonComponent.getInstance().getGson().toJson(item));

            mContext.startActivity(intent);
        }
    }


    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_view,parent,false);
        return new ItemListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemListViewHolder holder, int position) {
        Item item = mListData.get(position);

        holder.mName.setText(item.getName());
        holder.mQuantity.setText(String.valueOf(item.getQuantity()));
        holder.mType.setText(item.getType());
        holder.mStatus.setText(item.getStatus());
        holder.mId.setText(String.valueOf(item.getId()));

        holder.itemView.setTag(item.getId());
    }

    @Override
    public int getItemCount(){
        if(mListData==null){
            return 0;
        }
        return mListData.size();
    }

    public void setData(List<Item> data){
        mListData=data;
        notifyDataSetChanged();;
    }
}
