package com.example.birdproto.scheduleitem;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.birdproto.ItemClickListener;
import com.example.birdproto.ItemTextChangeListener;
import com.example.birdproto.R;

public class SchdHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView schdProgName;
    FloatingActionButton schdPrev, schdNext;
    EditText etTime;
    CheckBox schdChk;
    int holdTimeVal = 0;
    boolean canSet = true;

    ItemClickListener itemClickListener;
    ItemTextChangeListener itemTextChangeListener;

    public SchdHolder(@NonNull View itemView) {
        super(itemView);

        schdProgName = (TextView)itemView.findViewById(R.id.schd_pgm_name);

        schdPrev = (FloatingActionButton)itemView.findViewById(R.id.schd_item_prev);
        schdNext = (FloatingActionButton)itemView.findViewById(R.id.schd_item_next);
        etTime = (EditText)itemView.findViewById(R.id.schd_pgm_time);
        schdChk = (CheckBox)itemView.findViewById(R.id.schd_chkb);

        schdChk.setOnClickListener(this);

        //schdChk.setOnClickListener(this);

//        etTime.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String holdEt = etTime.getText().toString().trim();
//
//                if(holdEt.isEmpty()){
//                    canSet = false;
//                }
//                else {
//                    canSet = true;
//                }
//
//                if(canSet){
//                    holdTimeVal = Integer.parseInt(holdEt);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        schdPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int n = holdTimeVal;
//
//                if(n > 0)
//                n--;
//                holdTimeVal = n;
//                etTime.setText(n+"");
//            }
//        });
//        schdNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int p = holdTimeVal;
//
//                if(p < 999)
//                p++;
//                holdTimeVal = p;
//                etTime.setText(p+"");
//            }
//        });
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }
}
