package com.example.actionbarmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.act_add:
                Toast.makeText(this, "add is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_del:
                Toast.makeText(this, "delete is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_edit:
                Toast.makeText(this, "edit is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_imp:
                Toast.makeText(this, "import is clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
