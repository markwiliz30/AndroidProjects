package com.example.user.rfid_monitoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getIntent().hasExtra("read"))
        {
            TextView myText = (TextView)findViewById(R.id.textView);
            String text = getIntent().getExtras().getString("read");
            myText.setText(text);
        }
    }
}
