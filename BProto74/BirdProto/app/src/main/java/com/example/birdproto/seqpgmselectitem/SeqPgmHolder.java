package com.example.birdproto.seqpgmselectitem;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.birdproto.ItemClickListener;
import com.example.birdproto.ItemLongClickListener;
import com.example.birdproto.R;

public class SeqPgmHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener itemClickListener;
    CheckBox chkSeqPgmSelect;
    TextView pgmName;
    FloatingActionButton decDurationFab, incDurationFab;
    EditText etPgmDuration;

    public SeqPgmHolder(@NonNull View itemView) {
        super(itemView);

        chkSeqPgmSelect = (CheckBox)itemView.findViewById(R.id.chk_seq_pgm_select);
        pgmName = (TextView)itemView.findViewById(R.id.tv_seq_pgm_select);
        decDurationFab = (FloatingActionButton)itemView.findViewById(R.id.fab_prev_seq_pgm_select);
        incDurationFab = (FloatingActionButton)itemView.findViewById(R.id.fab_next_seq_pgm_select);
        etPgmDuration = (EditText)itemView.findViewById(R.id.et_seq_pgm_select);
    }

    public void setItemClickListener(ItemClickListener ic) {this.itemClickListener = ic;}

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }
}
