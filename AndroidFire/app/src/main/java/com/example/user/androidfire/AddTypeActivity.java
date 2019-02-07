package com.example.user.androidfire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddTypeActivity extends AppCompatActivity {

    TextView textViewUserName;
    EditText editTextTypeName;
    SeekBar seekBarRating;
    Button buttonAddType;

    ListView listViewTypes;

    DatabaseReference databaseTypes;

    List<Types> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        textViewUserName = (TextView) findViewById(R.id.txtUserName);
        editTextTypeName = (EditText) findViewById(R.id.txtTypeName);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);

        buttonAddType = (Button) findViewById(R.id.btnAddType);
        listViewTypes = (ListView) findViewById(R.id.lstviewTypes);

        Intent intent = getIntent();

        types = new ArrayList<>();

        String id = intent.getStringExtra(MainActivity.User_ID);
        String name = intent.getStringExtra(MainActivity.User_Name);

        textViewUserName.setText(name);

        databaseTypes = FirebaseDatabase.getInstance().getReference("Types").child(id);

        buttonAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveType();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                types.clear();

                for (DataSnapshot typeSnapshot: dataSnapshot.getChildren()){
                    Types type = typeSnapshot.getValue(Types.class);
                    types.add(type);
                }

                TypeList typeListAdapter = new TypeList(AddTypeActivity.this, types);
                listViewTypes.setAdapter(typeListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveType() {
        String typeName = editTextTypeName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(typeName)){
            String id = databaseTypes.push().getKey();

            Types type = new Types(id, typeName, rating);

            databaseTypes.child(id).setValue(type);

            Toast.makeText(this, "Type Saved", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Type name should not be empty", Toast.LENGTH_LONG).show();
        }
    }
}
