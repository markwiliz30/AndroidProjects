package com.example.udpcomm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button startButton;

    private EditText sourcePortEditText;
    private EditText destinationIpEditText;
    private EditText destinationPortEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);

        //nameEditText = (EditText) findViewById(R.id.txtName);
        sourcePortEditText = (EditText) findViewById(R.id.txtSPort);
        destinationIpEditText = (EditText) findViewById(R.id.txtDIP);
        destinationPortEditText = (EditText) findViewById(R.id.txtDPort);

        sourcePortEditText.setText("5555");
        destinationIpEditText.setText("192.168.1.2");
        destinationPortEditText.setText("2035");
    }

    @Override
    public void onClick(View v) {
        int sourcePort = Integer.parseInt(sourcePortEditText.getText().toString());
        String destinationIP = destinationIpEditText.getText().toString();
        int destinationPort = Integer.parseInt(destinationPortEditText.getText().toString());

        System.out.println(" | "+sourcePort+" | "+destinationIP+" | "+destinationPort);

        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("sourcePort", sourcePort);
        intent.putExtra("destinationIP", destinationIP);
        intent.putExtra("destinationPort", destinationPort);
        startActivity(intent);
    }
}
