package com.example.birdproto.inputlayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.birdproto.ProgramFragment;
import com.example.birdproto.R;
import com.example.birdproto.common.CurrentSelected;
import com.example.birdproto.common.PgmCollection;
import com.example.birdproto.programitem.PgmAdapter;

public class PgmDurationDialog extends AppCompatDialogFragment {
    private EditText etPgmDuration;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_input_pgm_duration, null);

        builder.setView(view)
                .setTitle("Input Program Duration")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String duration = etPgmDuration.getText().toString();
                        PgmCollection.totalPgmItem.get(CurrentSelected.selectedProgram).setPgmDuration(Integer.parseInt(duration));
                        CurrentSelected.PublicPgmadapter.notifyDataSetChanged();
                    }
                });

        etPgmDuration = view.findViewById(R.id.et_pgm_duration);
        etPgmDuration.setText(PgmCollection.totalPgmItem.get(CurrentSelected.selectedProgram).getPgmDuration()+"");

        return builder.create();
    }
}
