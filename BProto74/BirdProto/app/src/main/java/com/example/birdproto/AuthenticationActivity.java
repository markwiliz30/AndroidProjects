package com.example.birdproto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.birdproto.common.DeviceProtocol;
import com.example.birdproto.common.Protocol;

public class AuthenticationActivity extends AppCompatActivity {
    Button startCtrl;
    public DeviceProtocol deviceProtocol = new DeviceProtocol();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Protocol.cDeviceProt = deviceProtocol;

        startCtrl = (Button)findViewById(R.id.btn_strt_ctrl);

        startCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthenticationActivity.this, MainActivity.class);
                Protocol.cDeviceProt.startChannel();
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Protocol.cDeviceProt.stopChannel();
    }
}
