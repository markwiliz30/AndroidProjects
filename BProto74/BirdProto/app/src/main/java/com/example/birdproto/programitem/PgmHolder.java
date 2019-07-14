package com.example.birdproto.programitem;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.birdproto.ItemClickListener;
import com.example.birdproto.ItemLongClickListener;
import com.example.birdproto.R;

public class PgmHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView pgmName, pgmIndicator;
    public LinearLayout pgmContainer;
    FloatingActionButton pgmPrev, pgmNext;
    Button btnDuration;
    int timeVal = 0;
    boolean canSet = true;
    int select = 0;


    ItemClickListener itemClickListener;
    ItemLongClickListener itemLongClickListener;

    public PgmHolder(@NonNull View itemView) {
        super(itemView);

        pgmName = (TextView) itemView.findViewById(R.id.pgm_pgm_name);
        pgmIndicator = (TextView) itemView.findViewById(R.id.pgm_indicator);
        pgmContainer = (LinearLayout) itemView.findViewById(R.id.pgm_container);
        btnDuration = (Button) itemView.findViewById(R.id.btn_pgm_duration);
        pgmPrev = (FloatingActionButton)itemView.findViewById(R.id.pgm_item_prev);
        pgmNext = (FloatingActionButton)itemView.findViewById(R.id.pgm_item_next);

        pgmName.setOnClickListener(this);
        pgmName.setOnLongClickListener(this);


    }

    public void setItemClickListener(ItemClickListener ic) {this.itemClickListener = ic;}

    public void setItemLongClickListener(ItemLongClickListener ilc){
        this.itemLongClickListener = ilc;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        this.itemLongClickListener.onItemLongClick(v, getAdapterPosition());
        return true;
    }
}
