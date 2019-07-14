package com.example.birdproto;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.birdproto.common.SequenceCollection;
import com.example.birdproto.sequenceitem.SequenceAdapter;
import com.example.birdproto.sequenceitem.SequenceItem;

import java.util.ArrayList;
import java.util.Calendar;

public class PopupFragmentSequence extends Fragment implements View.OnClickListener {
    FloatingActionButton seqBackFab;
    Button s1Start, s1End, s2Start, s2End, s3Start, s3End, s4Start, s4End, s5Start, s5End, s6Start, s6End, s7Start, s7End, s8Start, s8End, s9Start, s9End, s10Start, s10End;
    Button seq1Btn, seq2Btn, seq3Btn, seq4Btn, seq5Btn, seq6Btn, seq7Btn, seq8Btn, seq9Btn, seq10Btn;
    String selector;
    SequenceAdapter adapter;
    RecyclerView listItems;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

    TimePickerDialog.OnTimeSetListener mTimeListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.popup_fragment_sequence, container, false);
        Calendar Cal;

        seqBackFab = (FloatingActionButton) view.findViewById(R.id.seq_back_fab);

        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                switch (selector){
                    case "s1s":
                        s1Start.setText(time);
                        break;
                    case "s1e":
                        s1End.setText(time);
                        break;
                    case "s2s":
                        s2Start.setText(time);
                        break;
                    case "s2e":
                        s2End.setText(time);
                        break;
                    case "s3s":
                        s3Start.setText(time);
                        break;
                    case "s3e":
                        s3End.setText(time);
                        break;
                    case "s4s":
                        s4Start.setText(time);
                        break;
                    case "s4e":
                        s4End.setText(time);
                        break;
                    case "s5s":
                        s5Start.setText(time);
                        break;
                    case "s5e":
                        s5End.setText(time);
                        break;
                    case "s6s":
                        s6Start.setText(time);
                        break;
                    case "s6e":
                        s6End.setText(time);
                        break;
                    case "s7s":
                        s7Start.setText(time);
                        break;
                    case "s7e":
                        s7End.setText(time);
                        break;
                    case "s8s":
                        s8Start.setText(time);
                        break;
                    case "s8e":
                        s8End.setText(time);
                         break;
                    case "s9s":
                        s9Start.setText(time);
                        break;
                    case "s9e":
                        s9End.setText(time);
                        break;
                    case "s10s":
                        s10Start.setText(time);
                        break;
                    case "s10e":
                        s10End.setText(time);
                        break;
                }
            }
        };

        seqBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)v.getContext()).finish();
            }
        });

        adapter = new SequenceAdapter(getActivity(), SequenceCollection.sequenceList);

        listItems = (RecyclerView) view.findViewById(R.id.sequence_rv);
        listItems.setItemViewCacheSize(25);
        listItems.setLayoutManager(layoutManager);

        listItems.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        Calendar Cal = Calendar.getInstance();
        int Hour = Cal.get(Calendar.HOUR_OF_DAY);
        int Minute = Cal.get(Calendar.MINUTE);
        TimePickerDialog dialog;

        Intent i = new Intent(getContext(), PopupActivity.class);

        switch (v.getId()){
//            case R.id.seq1_stime_btn:
//                dialog = new TimePickerDialog(getContext(), mTimeListener, Hour, Minute, DateFormat.is24HourFormat(getActivity()));
//                selector = "s1s";
//                dialog.show();
//                break;
        }
    }

    private ArrayList<SequenceItem> getSequenceItems() {
        ArrayList<SequenceItem> sequenceItems = new ArrayList<>();

        SequenceItem p = new SequenceItem();
        p.setName("1");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("2");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("3");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("4");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("5");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("6");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("7");
        sequenceItems.add(p);

        p = new SequenceItem();
        p.setName("8");
        sequenceItems.add(p);

        return sequenceItems;
    }
}
