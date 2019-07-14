package com.example.highlightrecycler.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.highlightrecycler.Common.Common;
import com.example.highlightrecycler.Interface.ItemClickListener;
import com.example.highlightrecycler.Model.Item;
import com.example.highlightrecycler.R;

import java.util.List;

class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    public ImageView imageView;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CustomViewHolder(View itemView){
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text_view);
        imageView = (ImageView)itemView.findViewById(R.id.image_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    List<Item> items;
    Context context;

    int row_index = -1; // Default no row choose

    public CustomAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_item, viewGroup, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.textView.setText(items.get(i).getName());

        if(!items.get(i).isChecked()){
            customViewHolder.imageView.setImageResource(R.drawable.ic_clear_black_24dp);
        }else
        {
            customViewHolder.imageView.setImageResource(R.drawable.ic_check_black_24dp);
        }

        customViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index = position; // set row indes to selected position
                Common.currentItem = items.get(position); //set current item is item selection
                notifyDataSetChanged(); //Made effect on Recycler View's Adapter
            }
        });

        //set highlight color
        if(row_index == i){
            customViewHolder.itemView.setBackgroundColor(Color.parseColor("#F8F8FA"));
            customViewHolder.textView.setTextColor(Color.parseColor("#c5c5c7"));
        }else
        {
            customViewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            customViewHolder.textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
