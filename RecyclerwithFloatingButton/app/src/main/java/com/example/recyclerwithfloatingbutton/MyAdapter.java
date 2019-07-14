package com.example.recyclerwithfloatingbutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder>{
    Context c;
    ArrayList<Player> players;
    ArrayList<Player> checkedPlayers = new ArrayList<>();

    public MyAdapter(Context c, ArrayList<Player> players) {
        this.c = c;
        this.players = players;
    }

    //VIEWHOLDER IS INITIALIZED
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model, null);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    //DATA IS BOUND TO VIEWS
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.nameTxt.setText(players.get(i).getName());
        myHolder.posTxt.setText(players.get(i).getPosition());
        myHolder.img.setImageResource(players.get(i).getImage());

        myHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk = (CheckBox) v;

                //Check if its checked or not
                if(chk.isChecked())
                {
                    checkedPlayers.add(players.get(pos));
                }else if(!chk.isChecked())
                {
                    checkedPlayers.remove(players.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
