package com.example.birdproto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.sqldatabase.DatabaseHelper;

import java.util.ArrayList;

public class PopupImportFragment extends Fragment {
    DatabaseHelper blDb;
    FloatingActionButton backbtn;
    private int fragMode = 0;
    TextView iTitle, iSubTitle;
    ArrayList<PgmItem> pgmItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.popup_fragment_import, container, false);
        backbtn = (FloatingActionButton)view.findViewById(R.id.import_back_fab);
        iTitle = (TextView)view.findViewById(R.id.import_title);
        iSubTitle = (TextView)view.findViewById(R.id.import_list_title);

        blDb = new DatabaseHelper(getContext());

        fragMode = getArguments().getInt("fragMode");
        if(fragMode == 2){
            iTitle.setText("Import Setup");
            iSubTitle.setText("List of saved setup(s)");
        }else {
            iTitle.setText("Import Programs");
            iSubTitle.setText("List of saved program(s)");
            viewAllPrograms();
        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)v.getContext()).finish();
            }
        });
        return view;
    }

    public void viewAllPrograms(){
        Cursor res = blDb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        pgmItems = new ArrayList<>();
        while (res.moveToNext()){
            PgmItem pgmItem = new PgmItem("0");
            pgmItem.setPgm(res.getString(2));
            pgmItem.setPgmDuration(Integer.parseInt(res.getString(3)));
            pgmItems.add(pgmItem);
        }
    }
}
