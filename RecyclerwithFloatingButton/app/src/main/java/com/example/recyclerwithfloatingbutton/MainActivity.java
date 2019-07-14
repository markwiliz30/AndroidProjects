package com.example.recyclerwithfloatingbutton;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    StringBuffer sb = null;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new MyAdapter(this, getPlayers());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb = new StringBuffer();

                for(Player p : adapter.checkedPlayers){
                    sb.append(p.getName());
                    sb.append("\n");
                }

                if(adapter.checkedPlayers.size() > 0)
                {
                    Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Check Players", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Recycler
        RecyclerView rv = findViewById(R.id.myRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        rv.setAdapter(adapter);
    }

    private ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();

        Player p = new Player("John Doe","Striker", R.drawable.android_left);
        players.add(p);

        p = new Player("Karrie","Marksman", R.drawable.android_right);
        players.add(p);

        p = new Player("Uranus","Tank", R.drawable.android_left);
        players.add(p);

        p = new Player("Gussion","Assasin", R.drawable.android_right);
        players.add(p);

        p = new Player("Harith","Mage", R.drawable.android_left);
        players.add(p);

        return players;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
