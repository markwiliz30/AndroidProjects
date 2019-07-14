package com.example.birdproto;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.birdproto.common.DeviceAddress;
import com.example.birdproto.common.DeviceProtocol;
import com.example.birdproto.common.Protocol;
import com.example.birdproto.communication.Channel;
import com.example.birdproto.communication.OnSocketListener;

import java.net.InetSocketAddress;
import java.net.SocketException;

public class TestFragment extends Fragment implements View.OnClickListener {
    Button btnRun, btnStop, btnReset;
    FloatingActionButton fabTestPrev, fabTestNext;
    EditText etTestProg;

    int testSel = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        btnRun = (Button)view.findViewById(R.id.test_btn_run);
        btnStop = (Button)view.findViewById(R.id.test_btn_stop);
        btnReset = (Button)view.findViewById(R.id.btn_reset);

        fabTestPrev = (FloatingActionButton)view.findViewById(R.id.fab_test_prev);
        fabTestNext = (FloatingActionButton)view.findViewById(R.id.fab_test_next);
        etTestProg = (EditText)view.findViewById(R.id.etxt_test_prog);

        btnRun.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        fabTestPrev.setOnClickListener(this);
        fabTestNext.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        byte command = 0x02;
        byte[] data = new byte[]{(byte) 0x01, (byte) 0x01,(byte)0 ,(byte) 0,(byte) 0,(byte) 0x01};
        Protocol.cDeviceProt.transferData(command, data);
    }

    @Override
    public void onClick(View v) {
        byte command;
        byte[] data;
        switch (v.getId()){
            case R.id.test_btn_run:
                command = 0x02;

                if(testSel == 1){
                    data = new byte[]{(byte) 0x01, (byte) 0x01,(byte)255 ,(byte) 255,(byte) 255,(byte) 0x01};
                    Protocol.cDeviceProt.transferData(command, data);
                }else if(testSel == 2){
                    data = new byte[]{(byte) 0x01, (byte) 0x01,(byte) 0x80,(byte) 0x80,(byte) 0x80,(byte) 0x01};
                    Protocol.cDeviceProt.transferData(command, data);
                }
                break;
            case R.id.test_btn_stop:
                break;
            case R.id.fab_test_prev:
                if(testSel > 1){
                    testSel--;
                    etTestProg.setText(testSel+"");
                }
                break;
            case R.id.fab_test_next:
                if(testSel < 2){
                    testSel++;
                    etTestProg.setText(testSel+"");
                }
                break;
            case R.id.btn_reset:
                command = 0x02;
                data = new byte[]{(byte) 0x01, (byte) 0x01,(byte)0 ,(byte) 0,(byte) 0,(byte) 0x01};
                Protocol.cDeviceProt.transferData(command, data);
                break;
        }
    }
}
