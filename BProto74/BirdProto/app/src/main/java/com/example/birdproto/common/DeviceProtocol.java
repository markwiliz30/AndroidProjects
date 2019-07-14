package com.example.birdproto.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.birdproto.communication.Channel;
import com.example.birdproto.communication.OnSocketListener;

import java.net.InetSocketAddress;
import java.net.SocketException;


public class DeviceProtocol implements android.os.Handler.Callback, OnSocketListener {
    byte version = 0x01;
    byte[] byteDataLength = new byte[2];
    byte[] signature = new byte[]{(byte)0xAA, (byte)0x42, (byte)0x49, (byte)0x52, (byte)0x44, (byte)0x4C, (byte)0x49, (byte)0x47, (byte)0x48, (byte)0x54};
    byte[] data;
    byte command;

    byte[] dataToSend;
    int dataLength;

    private InetSocketAddress address;
    private Channel channel;
    private android.os.Handler handler;

    public android.os.Handler postDelayedSendToModule = new android.os.Handler();

    boolean canAccess = false, isRecognized = false;
    public boolean canSend = false;

    public String startChannel(){
        address = new InetSocketAddress(DeviceAddress.destinationIP, DeviceAddress.destinationPort);
        handler = new android.os.Handler(this);

        if(null == channel)
        {
            try {
                channel = new Channel(this);
                channel.bind(DeviceAddress.sourcePort);
                channel.start();
                return "Channel started";
            }
            catch (SocketException e){
                String error = e.toString();
                return error;
                //getActivity().finish();
            }
        }
        return "Channel is not null";
    }

    public void stopChannel(){
        if(null == channel){
            channel.stop();
        }
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

    private Runnable run = new Runnable() {

        @Override
        public void run() {
            if(canAccess)
            {
                sendData();
            }
        }
    };

    public Runnable sendToModule = new Runnable() {

        @Override
        public void run() {
            if(canSend)
            {
                sendAll();
                canSend = false;
            }
        }
    };

    private Runnable postSendData = new Runnable() {

        @Override
        public void run() {
            if(canAccess)
            {
                sendData();
            }
        }
    };

    private Runnable postSendAck = new Runnable() {

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

    public byte[] setData(int lXData, int lYData, int lLData)
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
    }

    private void sendSignature()
    {
        channel.sendTo(address, signature);
    }

    private void sendAll()
    {
        sendSignature();
        android.os.Handler postDelayedSendData = new android.os.Handler();
        postDelayedSendData.postDelayed(postSendData, 100);

        android.os.Handler postDelayedSendAck = new Handler();
        postDelayedSendAck.postDelayed(postSendAck, 200);
        canAccess = false;
        isRecognized = false;
    }

    public void transferData(byte exCommand, byte[] exData){
        command = exCommand;
        data = exData;
        dataLength = data.length;

        sendAll();
    }

    public void transferDataWithDelay(byte exCommand, byte[] exData){
        command = exCommand;
        data = exData;
        dataLength = data.length;
        canSend = true;

        postDelayedSendToModule.postDelayed(sendToModule, 100);
    }

    @Override
    public boolean handleMessage(Message msg) {
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

        return false;
    }

    @Override
    public void onReceived(String text) {
        Bundle bundle = new Bundle();
        bundle.putString("text", text);

        Message msg = new Message();
        msg.setData(bundle);

        handler.sendMessage(msg);
    }
}
