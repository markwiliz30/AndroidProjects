package com.example.birdproto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.birdproto.common.PgmCollection;
import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.seqpgmselectitem.SeqPgmAdapter;

import java.util.ArrayList;

public class PopupSeqProgSelectFragment extends Fragment {
    FloatingActionButton backBtn;
    TextView sequenceName;
    String strSeqName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.popup_fragment_seq_prog_select, container, false);
        strSeqName = getArguments().getString("SequenceName");

        backBtn = (FloatingActionButton)view.findViewById(R.id.fab_seq_prog_select_back);
        sequenceName = (TextView)view.findViewById(R.id.tv_seq_name_title);

        sequenceName.setText("Sequence - "+strSeqName);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)v.getContext()).finish();
            }
        });

        SeqPgmAdapter adapter = new SeqPgmAdapter(getActivity(), PgmCollection.totalPgmItem);

        RecyclerView listItems = (RecyclerView) view.findViewById(R.id.seq_prg_select_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listItems.setItemViewCacheSize(25);
        listItems.setLayoutManager(layoutManager);

        listItems.setAdapter(adapter);

        return view;
    }
}
