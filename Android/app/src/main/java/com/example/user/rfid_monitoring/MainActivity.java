package com.example.user.rfid_monitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int adding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIncrement = (Button)findViewById(R.id.btnIncrement);
        Button btnShowSecond = (Button)findViewById(R.id.btnShowSecond);
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtView = (TextView)findViewById(R.id.txtView);

                adding++;
                txtView.setText(adding + "");
            }
        });
        btnShowSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startIntent.putExtra("read","hello me");
                startActivity(startIntent);
            }
        });

    }
}
