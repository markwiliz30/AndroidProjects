package com.example.birdproto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdproto.common.CurrentSelected;
import com.example.birdproto.common.PgmCollection;
import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.sqldatabase.DatabaseHelper;

public class PopupSaveFragment extends Fragment {
    DatabaseHelper blDB;
    EditText etFileName;
    Button cancelBtn, saveBtn;
    TextView sTitle;
    private int fragMode = 0;
    private String rxPgmName;
    private int rxPgmDuration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.popup_fragment_save, container, false);
        blDB = new DatabaseHelper(getContext());
        saveBtn = view.findViewById(R.id.save_save_btn);
        cancelBtn = view.findViewById(R.id.save_cancel_btn);
        sTitle = view.findViewById(R.id.save_subtitle);
        etFileName = view.findViewById(R.id.et_save_file_name);

        fragMode = getArguments().getInt("fragMode");
        rxPgmName = getArguments().getString("pgmName");
        rxPgmDuration = getArguments().getInt("pgmDuration", -1);

        if(fragMode == 2){
            sTitle.setText("Setup file name");
        }else{
            sTitle.setText("Program file name");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragMode == 2){

                }else {
                    String strFileName = etFileName.getText().toString();
                    boolean isInserted = blDB.insertData(strFileName, rxPgmName, rxPgmDuration+"");

                    if(isInserted){
                        Toast.makeText(getContext(), "Program successfully saved", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Saving program failed", Toast.LENGTH_SHORT).show();
                    }
                    ((FragmentActivity)v.getContext()).finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)v.getContext()).finish();
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}
