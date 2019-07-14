package com.example.birdproto.seqpgmselectitem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.birdproto.ItemClickListener;
import com.example.birdproto.R;
import com.example.birdproto.common.CurrentSelected;
import com.example.birdproto.common.SequenceCollection;
import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.sequenceitem.SequenceItem;

import java.util.ArrayList;

public class SeqPgmAdapter extends RecyclerView.Adapter<SeqPgmHolder> {
    Context c;
    ArrayList<PgmItem> pgmItems;

    public SeqPgmAdapter(Context c, ArrayList<PgmItem> pgmItems) {
        this.c = c;
        this.pgmItems = pgmItems;
    }

    @NonNull
    @Override
    public SeqPgmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View itemView = inflater.inflate(R.layout.seq_pgm_select_item, viewGroup,false);
        return new SeqPgmHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SeqPgmHolder seqPgmHolder, int i) {
        final PgmItem pgmItem = pgmItems.get(i);
        final SequenceItem selectedSeq = SequenceCollection.sequenceList.get(CurrentSelected.selectedSequence);
        seqPgmHolder.pgmName.setText("Program - "+pgmItem.getPgm());
        //seqPgmHolder.etPgmDuration.setText(selectedSeq.pgmList.);

        seqPgmHolder.etPgmDuration.setText(pgmItem.getPgmDuration()+"");

        seqPgmHolder.etPgmDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String holdEt = seqPgmHolder.etPgmDuration.getText().toString().trim();

                pgmItem.pgmDuration = Integer.parseInt(holdEt);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(selectedSeq.pgmList.contains(pgmItem)){
            seqPgmHolder.chkSeqPgmSelect.setChecked(true);
            seqPgmHolder.decDurationFab.setEnabled(true);
            seqPgmHolder.incDurationFab.setEnabled(true);
            seqPgmHolder.etPgmDuration.setEnabled(true);
        }else {
            seqPgmHolder.chkSeqPgmSelect.setChecked(false);
            seqPgmHolder.decDurationFab.setEnabled(false);
            seqPgmHolder.incDurationFab.setEnabled(false);
            seqPgmHolder.etPgmDuration.setEnabled(false);
        }

        seqPgmHolder.incDurationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int pgmIndex;
//                PgmItem LpgmItem;
//                for (PgmItem item: selectedSeq.pgmList) {
//                    if(item.pgm.equals(pgmItem.pgm)){
//                        pgmIndex = selectedSeq.pgmList.indexOf(item);
//                        LpgmItem = selectedSeq.pgmList.get(pgmIndex);
//                        if(LpgmItem.pgmDuration < 999){
//                            int a = 100;
//                            LpgmItem.pgmDuration = a;
//                            seqPgmHolder.etPgmDuration.setText(pgmItem.getPgmDuration()+"");
//                        }
//                        break;
//                    }
//                }

                if(pgmItem.pgmDuration < 999){
                    pgmItem.pgmDuration++;
                    seqPgmHolder.etPgmDuration.setText(pgmItem.getPgmDuration()+"");
                }
            }
        });

        seqPgmHolder.decDurationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pgmItem.pgmDuration > 0){
                    pgmItem.pgmDuration--;
                    seqPgmHolder.etPgmDuration.setText(pgmItem.getPgmDuration()+"");
                }
            }
        });

        seqPgmHolder.chkSeqPgmSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seqPgmHolder.chkSeqPgmSelect.isChecked()){
                    if(!selectedSeq.pgmList.contains(pgmItem)){
                        selectedSeq.pgmList.add(pgmItem);
                        seqPgmHolder.decDurationFab.setEnabled(true);
                        seqPgmHolder.incDurationFab.setEnabled(true);
                        seqPgmHolder.etPgmDuration.setEnabled(true);
                        Toast.makeText(v.getContext(), "Program - "+pgmItem.getPgm()+" is added to the sequence", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(v.getContext(), "Program - "+pgmItem.getPgm()+" is already added to the sequence", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if(selectedSeq.pgmList.contains(pgmItem)){
                        selectedSeq.pgmList.remove(pgmItem);
                        seqPgmHolder.decDurationFab.setEnabled(false);
                        seqPgmHolder.incDurationFab.setEnabled(false);
                        seqPgmHolder.etPgmDuration.setEnabled(false);
                        Toast.makeText(v.getContext(), "Program - "+pgmItem.getPgm()+" is removed to the sequence", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(v.getContext(), "Program - "+pgmItem.getPgm()+" is already removed to the sequence", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        seqPgmHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pgmItems.size();
    }
}
