package com.example.user.androidfire;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String User_Name = "username";
    public static final String User_ID = "userid";

    EditText txtRfid;
    Button btnAdd;
    Spinner spinnerRfid;

    DatabaseReference databaseUser;

    ListView listViewUser;

    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseUser = FirebaseDatabase.getInstance().getReference("androidUser");

        txtRfid = (EditText) findViewById(R.id.txtRfid);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        spinnerRfid = (Spinner) findViewById(R.id.spinnerRfid);

        listViewUser = (ListView) findViewById(R.id.lstviewUser);

        userList = new ArrayList<>();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = userList.get(position);

                Intent intent = new Intent(getApplicationContext(), AddTypeActivity.class);

                intent.putExtra(User_ID, user.getUserId());
                intent.putExtra(User_Name, user.getUserName());

                startActivity(intent);
            }
        });

        listViewUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                User user = userList.get(position);

                showUpdateDialog(user.getUserId(), user.getUserName());
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userList.clear();

                for (DataSnapshot userSnapShot: dataSnapshot.getChildren())
                {
                    User user = userSnapShot.getValue(User.class);

                    userList.add(user);
                }

                UserList adapter = new UserList(MainActivity.this, userList);
                listViewUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private  void showUpdateDialog(final String userId, String userName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpadate);
        final Spinner spinnerTypes = (Spinner) dialogView.findViewById(R.id.spinnerType);
        final Button buttonDelete = (Button)  dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating User " + userName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String type = spinnerTypes.getSelectedItem().toString();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name required");
                    return;
                }

                updateArtist(userId, name, type);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(userId);
            }
        });

    }

    private void deleteUser(String userId) {
        DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("androidUser").child(userId);
        DatabaseReference drTypes = FirebaseDatabase.getInstance().getReference("Types").child(userId);

        drUser.removeValue();
        drTypes.removeValue();

        Toast.makeText(this, "User Deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateArtist(String id, String name, String type){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("androidUser").child(id);

        User user = new User(id, name, type);

        databaseReference.setValue(user);

        Toast.makeText(this, "Updated sucessfully", Toast.LENGTH_LONG).show();

        return  true;
    }

    private void addUser(){
        String rfId = txtRfid.getText().toString().trim();
        String userType = spinnerRfid.getSelectedItem().toString();

        if(!TextUtils.isEmpty(rfId))
        {
           String id = databaseUser.push().getKey();

           User user = new User(id, rfId, userType);

           databaseUser.child(id).setValue(user);

           Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Please Enter RFID", Toast.LENGTH_LONG).show();
        }
    }
}
