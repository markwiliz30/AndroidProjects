package com.example.birdproto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdproto.common.CurrentSelected;
import com.example.birdproto.common.DeviceProtocol;
import com.example.birdproto.common.PgmCollection;
import com.example.birdproto.programitem.PgmAdapter;
import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.sequenceitem.SequenceItem;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ProgramFragment extends Fragment {
    TextView textView;
    private ActionMode mActionMode;
    public PgmAdapter adapter;
    public RecyclerView listItems;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_program, container, false);

        textView = (TextView) view.findViewById(R.id.pgm_list_title);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mActionMode != null){
                    return false;
                }


                return false;
            }
        });

        adapter = new PgmAdapter(getActivity(), PgmCollection.totalPgmItem);

        listItems = (RecyclerView) view.findViewById(R.id.pgm_recycler);
        listItems.setItemViewCacheSize(25);
        listItems.setLayoutManager(layoutManager);

        listItems.setAdapter(adapter);

        CurrentSelected.PublicPgmadapter = adapter;

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = new Intent(getContext(), PopupActivity.class);

        switch (id){
            case R.id.act_pgm_add:
                i.putExtra("Mode", 0);
                startActivityForResult(i, 1);
                //startActivity(i);
                //((PopupActivity)getContext()).setBackDisabled(true);
                break;
            case R.id.act_pgm_import:
                i.putExtra("Mode", 3);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu_program, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private ArrayList<PgmItem> getPgmItems() {
        ArrayList<PgmItem> pgmItems = new ArrayList<>();

//        PgmItem p = new PgmItem("01");
//        pgmItems.add(p);
//
//        p = new PgmItem("02");
//        pgmItems.add(p);
//
//        p = new PgmItem("03");
//        pgmItems.add(p);
//
//        p = new PgmItem("04");
//        pgmItems.add(p);
//
//        p = new PgmItem("05");
//        pgmItems.add(p);
//
//        p = new PgmItem("06");
//        pgmItems.add(p);
//
//        p = new PgmItem("07");
//        pgmItems.add(p);
//
//        p = new PgmItem("08");
//        pgmItems.add(p);

        pgmItems = PgmCollection.totalPgmItem;

        return pgmItems;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
