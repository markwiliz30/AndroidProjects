package com.example.birdproto.programitem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Color;
import android.sax.StartElementListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.birdproto.DrawerLocker;
import com.example.birdproto.ItemClickListener;
import com.example.birdproto.ItemLongClickListener;
import com.example.birdproto.MainActivity;
import com.example.birdproto.PopupActivity;
import com.example.birdproto.R;
import com.example.birdproto.common.CurrentSelected;
import com.example.birdproto.inputlayout.PgmDurationDialog;

import java.util.ArrayList;

public class PgmAdapter extends RecyclerView.Adapter<PgmHolder> {
    Context c;
    ArrayList<PgmItem> pgmItems;
    ArrayList<PgmItem> selectedPgmItems = new ArrayList<>();
    int row_index = -1;
    boolean isSelected = false;
    boolean multiSelect = false;
    boolean isClicked = true;
    PgmHolder exPgmHolder;
    PgmItem transferPgmItem;
    private View myView;
    int mode;

    public PgmAdapter(Context c, ArrayList<PgmItem> pgmItems) {
        this.c = c;
        this.pgmItems = pgmItems;
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            multiSelect = true;
            actionMode.getMenuInflater().inflate(R.menu.action_menu_second_program, menu);
            actionMode.setTitle("Choose Option");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.pgm_menu2_edit:
//                    if(multiSelect){
//                        menuItem.setEnabled(false);
//                    }

                    //PgmItem pgmItem = new PgmItem("01");
                    //PgmCollection.pgmItemArrayList.add(pgmItem);

                    if(selectedPgmItems.size() == 1){
                        for (PgmItem item: selectedPgmItems) {
                            transferPgmItem = item;
                        }
                        mode = 1;
                        Intent i = new Intent(myView.getContext(), PopupActivity.class);
                        i.putExtra("PgmName", transferPgmItem.getPgm());
                        //i.putExtra("Steps", transferPgmItem.getStepList());
                        i.putExtra("Mode", mode);
                        (myView.getContext()).startActivity(i);
                        actionMode.finish();
                    }else if(selectedPgmItems.size() == 0){
                        Toast.makeText(myView.getContext(), "Please choose a program", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(myView.getContext(), "Please choose one program only", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.pgm_menu2_delete:
                    final ActionMode lActionMode = actionMode;
                    AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());
                    builder.setTitle("Attention!")
                            .setMessage("Do you want to delete the selected Program(s)?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    removeSelectedItems();
                                    notifyDataSetChanged();
                                    lActionMode.finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                case R.id.pgm_menu2_save:
                    if(selectedPgmItems.size() == 1){
                        for (PgmItem item: selectedPgmItems) {
                            transferPgmItem = item;
                        }
                        mode = 2;
                        Intent i = new Intent(myView.getContext(), PopupActivity.class);
                        i.putExtra("PgmName", transferPgmItem.getPgm());
                        i.putExtra("PgmDuration", transferPgmItem.getPgmDuration());
                        //i.putExtra("Steps", transferPgmItem.getStepList());
                        i.putExtra("Mode", mode);
                        (myView.getContext()).startActivity(i);
                    }else if(selectedPgmItems.size() == 0){
                        Toast.makeText(myView.getContext(), "Please choose a program", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(myView.getContext(), "Please choose one program only", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.pgm_menu2_select_all:
                    boolean selAllIsChecked = menuItem.isChecked();
                    selAllIsChecked = !selAllIsChecked;
                    menuItem.setChecked(selAllIsChecked);

                    selectedPgmItems.clear();
                    if(selAllIsChecked){
                        selectedPgmItems.addAll(pgmItems);
                        for (PgmItem item: selectedPgmItems) {
                            if(item.equals(selectedPgmItems.get(row_index))){
                                item.setSelected(false);
                            }else {
                                item.setSelected(true);
                            }
                        }
                        notifyDataSetChanged();
                    }else {
                        selectedPgmItems.removeAll(pgmItems);
                        for (PgmItem item: pgmItems) {
                            if(item.equals(pgmItems.get(row_index))){
                                item.setSelected(true);
                            }else {
                                item.setSelected(false);
                            }
                        }
                        notifyDataSetChanged();
                    }

                    return true;
                    default:
                        return false;

            }
        }

        private void removeSelectedItems(){
            for (int i = 0; i<selectedPgmItems.size();i++)
            {
                PgmItem item = selectedPgmItems.get(i);
                pgmItems.remove(item);
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            multiSelect = false;
            selectedPgmItems.clear();
//            for (int i = 0; i<pgmItems.size();i++){
//                PgmItem item = pgmItems.get(i);
//                pgmItems.remove(i);
//            }
            notifyDataSetChanged();
            ((DrawerLocker)myView.getContext()).setDrawerEnabled(true);
        }
    };

    @NonNull
    @Override
    public PgmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View itemView = inflater.inflate(R.layout.pgm_item, viewGroup, false);
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pgm_item, null);
        //PgmHolder holder = new PgmHolder(v);
        return new PgmHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PgmHolder pgmHolder, int i) {
        //pgmHolder.pgmName.setText("PGM-"+ pgmItems.get(i).getPgm());
        final int finalI = i;
        final PgmItem pgmItem = pgmItems.get(i);
        exPgmHolder = pgmHolder;

//        pgmHolder.etPgmTime.setText(pgmItem.getPgmDuration()+"");

        pgmHolder.btnDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelected.selectedProgram =  finalI;
                PgmDurationDialog pgmDurationDialog = new PgmDurationDialog();
                pgmDurationDialog.show(((FragmentActivity)c).getSupportFragmentManager(), "pgmDuration");
                notifyDataSetChanged();
            }
        });

        pgmHolder.pgmPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = pgmHolder.timeVal;

                if(pgmItem.pgmDuration > 0){
                    pgmItem.pgmDuration--;
                    pgmHolder.btnDuration.setText(pgmItem.pgmDuration+"");
                }
            }
        });

        pgmHolder.pgmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = pgmHolder.timeVal;

                if(pgmItem.pgmDuration < 999){
                    pgmItem.pgmDuration++;
                    pgmHolder.btnDuration.setText(pgmItem.pgmDuration+"");
                }
            }
        });

        pgmHolder.pgmName.setText("Program - "+ pgmItems.get(i).getPgm());
        pgmHolder.btnDuration.setText(pgmItem.getPgmDuration()+"");
        pgmHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        pgmHolder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                row_index = pos;
                notifyDataSetChanged();
                ((AppCompatActivity)v.getContext()).startSupportActionMode(actionModeCallbacks);
                myView = v;
                ((DrawerLocker)v.getContext()).setDrawerEnabled(false);
            }
        });

        pgmHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                    CurrentSelected.selectedProgram = finalI;
                    row_index = pos;
                    notifyDataSetChanged();

            }
        });

        if(!multiSelect){
            pgmItem.setIndicatorVisible(true);
            if(row_index == i){
                pgmItem.setSelected(!pgmItem.isSelected());
            }
            else{
                pgmItem.setSelected(false);
            }
        }else {
            pgmItem.setIndicatorVisible(false);
            if(row_index == i){
                pgmItem.setSelected(!pgmItem.isSelected());
            }
        }

        if(pgmItem.isSelected()){
            if(!selectedPgmItems.contains(pgmItem)){
                selectedPgmItems.add(pgmItem);
            }
            pgmHolder.pgmIndicator.setText("On");
            pgmHolder.itemView.setBackgroundColor(Color.parseColor("#797979"));
        }else {
            if(selectedPgmItems.contains(pgmItem)){
                selectedPgmItems.remove(pgmItem);
            }
            pgmHolder.pgmIndicator.setText("Off");
            pgmHolder.pgmName.setText("Program - "+ pgmItems.get(i).getPgm());
            pgmHolder.btnDuration.setText(pgmItems.get(i).getPgmDuration()+"");
            pgmHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if(pgmItem.isIndicatorVisible()){
            pgmHolder.pgmIndicator.setVisibility(View.VISIBLE);
        }else {
            pgmHolder.pgmIndicator.setVisibility(View.INVISIBLE);
        }

//           pgmHolder.pgmName.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   pgmItem.setSelected(!pgmItem.isSelected());
//                   if(pgmItem.isSelected()){
//                       pgmHolder.itemView.setBackgroundColor(Color.parseColor("#797979"));
//                   }else {
//                       pgmHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//                   }
//               }
//           });
        }

    @Override
    public int getItemCount() {
        return pgmItems.size();
    }
}
