package com.example.birdproto.sequenceitem;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.birdproto.PopupActivity;
import com.example.birdproto.R;
import com.example.birdproto.common.CurrentSelected;

import java.util.ArrayList;
import java.util.Calendar;

public class SequenceAdapter extends RecyclerView.Adapter<SequenceHolder> {
    Context c;
    ArrayList<SequenceItem> sequenceItems;
    TimePickerDialog.OnTimeSetListener mTimeListener;

    public SequenceAdapter(Context c, ArrayList<SequenceItem> sequenceItems) {
        this.c = c;
        this.sequenceItems = sequenceItems;
    }

    @NonNull
    @Override
    public SequenceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View itemView = inflater.inflate(R.layout.sequence_item, viewGroup, false);
        return new SequenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SequenceHolder sequenceHolder, final int i) {
        final SequenceItem sequenceItem = sequenceItems.get(i);
//        sequenceHolder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//
//            }
//        });

        sequenceHolder.seqBtn.setText("Sequence - "+sequenceItem.getName());

        String sHourHolder = sequenceItem.sHour+"";
        String sMinuteHolder = sequenceItem.sMinute+"";
        if(sMinuteHolder.length() == 1){
            sMinuteHolder = "0"+sMinuteHolder;
        }

        String eHourHolder = sequenceItem.eHour+"";
        String eMinuteHolder = sequenceItem.eMinute+"";
        if(eMinuteHolder.length() == 1){
            eMinuteHolder = "0"+eMinuteHolder;
        }

        String sTimeHolder = sHourHolder+":"+sMinuteHolder;
        String eTimeHolder = eHourHolder+":"+eMinuteHolder;

        sequenceHolder.sTimeBtn.setText(sTimeHolder);
        sequenceHolder.eTimeBtn.setText(eTimeHolder);

        sequenceHolder.seqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PopupActivity.class);
                CurrentSelected.selectedSequence = Integer.parseInt(sequenceItem.getName())-1;
                i.putExtra("Mode", 7);
                i.putExtra("SequenceName", sequenceItem.getName());
                c.startActivity(i);
            }
        });

        sequenceHolder.sTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        sequenceItems.get(i).setsHour(hourOfDay);
                        sequenceItems.get(i).setsMinute(minute);

                        String time;
                        String holdMinute = minute+"";
                        if(holdMinute.length() == 1){
                            holdMinute = "0"+holdMinute;
                        }
                        time = hourOfDay+":"+holdMinute;
                        sequenceHolder.sTimeBtn.setText(time);
                    }
                };

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog;
                dialog = new TimePickerDialog(v.getContext(), mTimeListener, hour, minute, DateFormat.is24HourFormat(v.getContext()));
                dialog.show();
            }
        });

        sequenceHolder.eTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        sequenceItems.get(i).seteHour(hourOfDay);
                        sequenceItems.get(i).seteMinute(minute);

                        String time;
                        String holdMinute = minute+"";
                        if(holdMinute.length() == 1){
                            holdMinute = "0"+holdMinute;
                        }
                        time = hourOfDay+":"+holdMinute;
                        sequenceHolder.eTimeBtn.setText(time);
                    }
                };

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog;
                dialog = new TimePickerDialog(v.getContext(), mTimeListener, hour, minute, DateFormat.is24HourFormat(v.getContext()));
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sequenceItems.size();
    }
}
