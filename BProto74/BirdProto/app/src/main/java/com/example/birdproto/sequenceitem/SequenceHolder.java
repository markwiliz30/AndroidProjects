package com.example.birdproto.sequenceitem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.birdproto.ItemClickListener;
import com.example.birdproto.R;

public class SequenceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener itemClickListener;
    Button seqBtn, sTimeBtn, eTimeBtn;

    public SequenceHolder(@NonNull View itemView) {
        super(itemView);

        seqBtn = (Button)itemView.findViewById(R.id.seq_prop_btn);
        sTimeBtn = (Button)itemView.findViewById(R.id.seq_stime_btn);
        eTimeBtn = (Button)itemView.findViewById(R.id.seq_etime_btn);
    }

    public void setItemClickListener(ItemClickListener ic) {this.itemClickListener = ic;}

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }
}
