package com.example.birdproto.scheduleitem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.birdproto.ItemClickListener;
import com.example.birdproto.R;
import com.example.birdproto.programitem.PgmHolder;

import java.util.ArrayList;

public class SchdAdapter extends RecyclerView.Adapter<SchdHolder> {
    Context c;
    ArrayList<SchdItem> schdItems;
    public ArrayList<SchdItem> checkedSchdItems = new ArrayList<>();
    int row_index = -1;
    boolean holdCanset;
    int holdTimeVal = 0;

    public SchdAdapter(Context c, ArrayList<SchdItem> schdItems) {
        this.c = c;
        this.schdItems = schdItems;
    }

    @NonNull
    @Override
    public SchdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schd_item, null);
        SchdHolder holder = new SchdHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SchdHolder schdHolder, int i) {
        schdHolder.schdProgName.setText("Program - " + schdItems.get(i).getSchd());
        schdHolder.schdPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = schdHolder.holdTimeVal;

                if(n > 0){
                    n--;
                    schdHolder.holdTimeVal = n;
                    schdHolder.etTime.setText(n+"");
                }
            }
        });

        schdHolder.etTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String holdEt = schdHolder.etTime.getText().toString().trim();

                if(holdEt.isEmpty()){
                    schdHolder.canSet = false;
                    schdHolder.schdChk.setChecked(false);
                }
                else {
                    schdHolder.canSet = true;
                }

                if(schdHolder.canSet){
                    schdHolder.holdTimeVal = Integer.parseInt(holdEt);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        schdHolder.schdNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = schdHolder.holdTimeVal;

                if(p < 999){
                    p++;
                    schdHolder.holdTimeVal = p;
                    schdHolder.etTime.setText(p+"");
                }
            }
        });

        schdHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Do actions
                CheckBox chk = (CheckBox)v;

                if(schdHolder.canSet && chk.isChecked()){
                    schdItems.get(pos).setSchdTime(schdHolder.holdTimeVal);
                    checkedSchdItems.add(schdItems.get(pos));
                }else if(!schdHolder.canSet){
                    schdHolder.schdChk.setChecked(false);
                    checkedSchdItems.remove(schdItems.get(pos));
                }
                else if(!schdHolder.schdChk.isChecked()){
                    checkedSchdItems.remove(schdItems.get(pos));
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return schdItems.size();
    }
}
