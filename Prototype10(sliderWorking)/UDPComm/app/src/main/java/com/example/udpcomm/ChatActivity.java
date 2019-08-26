package com.example.udpcomm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ChatActivity extends Activity implements View.OnClickListener, OnSocketListener, Handler.Callback, SeekBar.OnSeekBarChangeListener
{
    byte version = 0x01;
    byte[] byteDataLength = new byte[2];
    byte[] signature = new byte[]{(byte)0xAA, (byte)0x42, (byte)0x49, (byte)0x52, (byte)0x44, (byte)0x4C, (byte)0x49, (byte)0x47, (byte)0x48, (byte)0x54};
    byte[] data;
    byte command;
    int xAxis,yAxis,light;
    byte[] dataToSend;
    int dataLength;

    private int sourcePort;
    private String destinationIP;
    private int destinationPort;
    int millisDelay = 200;
    int validationCounter = 0;


    private InetSocketAddress address;

    private Channel channel;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;

    private SeekBar xSeekBar;
    private SeekBar ySeekBar;
    private SeekBar lSeekBar;


    private ListView messageListView;
    private ArrayAdapter<String> messageAdapter;

    private Handler handler;
    boolean canAccess = false;
    boolean isRecognized = false;
    boolean canSend = false;

    Handler postDelayedSendToModule = new Handler();;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        sourcePort = intent.getIntExtra("sourcePort", 1111);
        destinationIP = intent.getStringExtra("destinationIP");
        destinationPort = intent.getIntExtra("destinationPort", 2222);

        address = new InetSocketAddress(destinationIP, destinationPort);

        //messageEditText = (EditText) findViewById(R.id.txtMessage);
        button0 = (Button) findViewById(R.id.btn0);
        button0.setOnClickListener(this);

        button1 = (Button) findViewById(R.id.btn1);
        button1.setOnClickListener(this);

        button2 = (Button) findViewById(R.id.btn2);
        button2.setOnClickListener(this);

        button3 = (Button) findViewById(R.id.btn3);
        button3.setOnClickListener(this);

        button4 = (Button) findViewById(R.id.btn4);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.btn5);
        button5.setOnClickListener(this);

        button6 = (Button) findViewById(R.id.btn6);
        button6.setOnClickListener(this);

        button7 = (Button) findViewById(R.id.btn7);
        button7.setOnClickListener(this);

        button8 = (Button) findViewById(R.id.btn8);
        button8.setOnClickListener(this);

        button9 = (Button) findViewById(R.id.btn9);
        button9.setOnClickListener(this);

        xSeekBar = (SeekBar) findViewById(R.id.XSeekBar);
        xSeekBar.setOnSeekBarChangeListener(this);

        ySeekBar = (SeekBar) findViewById(R.id.YSeekBar);
        ySeekBar.setOnSeekBarChangeListener(this);

        lSeekBar = (SeekBar) findViewById(R.id.LSeekBar);
        lSeekBar.setOnSeekBarChangeListener(this);

        messageAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_message, R.id.messageTextView);

        messageListView = (ListView) findViewById(R.id.messageListView);
        messageListView.setAdapter(messageAdapter);

        handler = new Handler(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(null == channel)
        {
            try
            {
                channel = new Channel(this);
                channel.bind(sourcePort);
                channel.start();
            }
            catch (SocketException e)
            {
                e.printStackTrace();
                finish();
            }
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private byte[] dataLengthCounterHex(int byteCount)
    {
        byte lsd = 0x00;
        byte msd = 0x00;
        int counter = 0;
        byte[] returnedBytes = new byte[2];
        for(int i = 1; i <= byteCount; i++)
        {
            lsd++;
            counter++;
            if(counter > 255)
            {
                msd++;
                lsd = 0x00;
            }
        }
        returnedBytes[0] = lsd;
        returnedBytes[1] = msd;
        return returnedBytes;
    }

    Runnable run = new Runnable() {

        @Override
        public void run() {
            if(canAccess)
            {
                sendData();
            }
        }
    };

    Runnable sendToModule = new Runnable() {

        @Override
        public void run() {
            if(canSend)
            {
                sendAll();
                canSend = false;
            }
        }
    };

    private void sendSignature()
    {
        channel.sendTo(address, signature);

        messageAdapter.add(String.valueOf(signature));
        messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
    }
    private void sendData()
    {
        byteDataLength = dataLengthCounterHex(dataLength);
        int length = 1 + byteDataLength.length + 1 + data.length;
        dataToSend = new byte[length];
        for (int i = 0; i<length; i++) {
            dataToSend[i] = version;
            i++;
            for (int l = 0; l<byteDataLength.length; l++, i++)
            {
                dataToSend[i] = byteDataLength[l];
            }
            dataToSend[i] = command;
            i++;
            for (int m = 0; m<data.length; m++, i++)
            {
                dataToSend[i] = data[m];
            }

            channel.sendTo(address, dataToSend);
            messageAdapter.add(String.valueOf(dataToSend));
            messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
        }
    }

    private void sendFinalAck()
    {
        byte[] finalAcknowledment = new byte[2];
        byte O = 0x4f;
        byte K = 0x4b;
        finalAcknowledment[0] = O;
        finalAcknowledment[1] = K;

        channel.sendTo(address, finalAcknowledment);
        messageAdapter.add(String.valueOf(finalAcknowledment));
        messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
    }

    Runnable postSendData = new Runnable() {

        @Override
        public void run() {
            if(canAccess)
            {
                sendData();
            }
        }
    };

    Runnable postSendAck = new Runnable() {

        @Override
        public void run() {
            if(isRecognized)
            {
                if(isRecognized)
                {
                    sendFinalAck();
                }
            }
        }
    };

    private void sendAll()
    {
        sendSignature();
        Handler postDelayedSendData = new Handler();
        postDelayedSendData.postDelayed(postSendData, 100);

        Handler postDelayedSendAck = new Handler();
        postDelayedSendAck.postDelayed(postSendAck, 200);
        canAccess = false;
        isRecognized = false;
    }

    private byte[] setData(int lXData, int lYData, int lLData)
    {
        byte x = 0;
        byte y = 0;
        byte l = 0;

        for(int i = 0; i<lXData; i++)
        {
            x++;
        }

        for(int j = 0; j<lYData; j++)
        {
            y++;
        }

        for(int k = 0; k<lLData; k++)
        {
            l++;
        }

        byte[] movVal = new byte[]{x,y,l};

        return  movVal;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn0:
                command = 0x02;
                data = new byte[]{(byte) 0x01, (byte) 0x01,(byte) 0x00,(byte) 0x00,(byte) 0x00,(byte) 0x01};
                dataLength = data.length;

                sendAll();
                break;
            case R.id.btn1:
                command = 0x02;
                data = new byte[]{(byte) 0x01, (byte) 0x01,(byte) 0x80,(byte) 0x80,(byte) 0x80,(byte) 0x01};
                dataLength = data.length;

                sendAll();
                break;
            case R.id.btn2:
                command = 0x02;
                data = new byte[]{(byte) 0x01, (byte) 0x01,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0x01};
                dataLength = data.length;

                sendAll();
                break;
            case R.id.btn3:
                data = new byte[]{0x58, 0x33};

                channel.sendTo(address, data);

                messageAdapter.add(String.valueOf(data));
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                break;
            case R.id.btn4:
                data = new byte[]{0x58, 0x34};

                channel.sendTo(address, data);

                messageAdapter.add(String.valueOf(data));
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                break;
            case R.id.btn5:
                data = new byte[]{0x58, 0x35};

                channel.sendTo(address, data);

                messageAdapter.add(String.valueOf(data));
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                break;
            case R.id.btn6:
                data = new byte[]{0x58, 0x36};

                channel.sendTo(address, data);

                messageAdapter.add(String.valueOf(data));
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                break;
            case R.id.btn7:
                data = new byte[]{0x58, 0x37};

                channel.sendTo(address, data);

                messageAdapter.add(String.valueOf(data));
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                break;
            case R.id.btn8:
                data = new byte[]{0x58, 0x38};

                channel.sendTo(address, data);

                messageAdapter.add(String.valueOf(data));
                messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);
                break;
            case R.id.btn9:
                command = 0x0f;
                data = new byte[]{(byte) 0x0f0};
                dataLength = data.length;

                sendAll();
                break;
        }
    }

    @Override
    public void onReceived(String text)
    {
        Bundle bundle = new Bundle();
        bundle.putString("text", text);

        Message msg = new Message();
        msg.setData(bundle);

        handler.sendMessage(msg);
    }

    @Override
    public boolean handleMessage(Message msg)
    {
        Bundle bundle = msg.getData();
        String text = bundle.getString("text");
        String authComp = "AUD\n";
        String recogComp = "RED\n";

        if(text.equals(authComp))
        {
            canAccess = true;
        }

        if(text.equals(recogComp))
        {
            isRecognized = true;
        }
        messageAdapter.add(text);
        messageListView.smoothScrollToPosition(messageAdapter.getCount() - 1);

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_chat, menu);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != channel)
        {
            channel.stop();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        postDelayedSendToModule.removeCallbacksAndMessages(null);
        byte[] movData = new byte[3];
        command = 0x02;

        switch (seekBar.getId())
        {
            case R.id.XSeekBar:
                xAxis = xSeekBar.getProgress();
                movData = setData(xAxis, yAxis, light);
                data = new byte[]{(byte) 0x01, (byte) 0x01, movData[0], movData[1], movData[2],(byte) 0x01};
                dataLength = data.length;
                canSend = true;

                postDelayedSendToModule.postDelayed(sendToModule, 100);
                break;
            case R.id.YSeekBar:
                yAxis = ySeekBar.getProgress();
                movData = setData(xAxis, yAxis, light);
                data = new byte[]{(byte) 0x01, (byte) 0x01, movData[0], movData[1], movData[2],(byte) 0x01};
                dataLength = data.length;
                canSend = true;

                postDelayedSendToModule.postDelayed(sendToModule, 100);
                break;
            case R.id.LSeekBar:
                light = lSeekBar.getProgress();
                movData = setData(xAxis, yAxis, light);
                data = new byte[]{(byte) 0x01, (byte) 0x01, movData[0], movData[1], movData[2],(byte) 0x01};
                dataLength = data.length;
                canSend = true;

                postDelayedSendToModule.postDelayed(sendToModule, 100);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}