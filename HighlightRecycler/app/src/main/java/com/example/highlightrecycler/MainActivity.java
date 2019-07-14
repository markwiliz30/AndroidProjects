package com.example.highlightrecycler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.highlightrecycler.Adapter.CustomAdapter;
import com.example.highlightrecycler.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView list_item;
    RecyclerView.LayoutManager layoutManager;
    List<Item> items = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewActivity.class));
            }
        });

        //
        list_item = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        list_item.setHasFixedSize(true);
        list_item.setLayoutManager(layoutManager);

        getData();
    }

    private void getData() {
        //Genarate data
        for(int i = 0; i<10; i++){
            Item item = new Item();
            item.setName("Item"+i);
            if(i%2 == 0){
                item.setChecked(true);
            }else{
                item.setChecked(false);
            }
            items.add(item);

            //Create Adapter
            CustomAdapter adapter = new CustomAdapter(items, this);
            list_item.setAdapter(adapter);
        }
    }
}
