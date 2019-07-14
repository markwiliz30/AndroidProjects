package com.example.birdproto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdproto.common.DeviceProtocol;
import com.example.birdproto.common.PgmCollection;
import com.example.birdproto.common.Protocol;
import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.stepitem.StepItem;

import java.util.ArrayList;

public class PopupEditFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    Intent returnIntent;
    TextView pgmName;
    EditText stepEd, delayEd;
    FloatingActionButton backBtn, stepPrevBtn, stepNextBtn, delayPrevBtn, delayNextBtn;
    Button addStpBtn, positiveBtn, negativeBtn;
    SeekBar pSeekBar, tSeekBar, bSeekBar;
    View myView;
    int pVal,tVal,bVal;
    int step = 0, stepSize, delay = 0;
    int internalMode, currentItemPos;
    String strtext;
    PgmItem mPgmItem, unbindedPgmItem;
    StepItem mStepItem, currentStepItem = new StepItem(), newStepItem;

    ArrayList<Integer> holdPval = new ArrayList<>();
    ArrayList<Integer> holdTval = new ArrayList<>();
    ArrayList<Integer> holdBval = new ArrayList<>();
    ArrayList<Integer> holdDelayVal = new ArrayList<>();
    ArrayList<String> holdStepName = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.popup_fragment_edit, container, false);
        //ArrayList<StepItem> stepItems = new ArrayList<>();
        //stepItems = (ArrayList<StepItem>) getArguments().getSerializable("stepList");
        internalMode = getArguments().getInt("Mode");

        pgmName = (TextView)view.findViewById(R.id.edit_pgmname);

        pSeekBar = (SeekBar)view.findViewById(R.id.edit_pan_sb);
        pSeekBar.setOnSeekBarChangeListener(this);

        tSeekBar = (SeekBar)view.findViewById(R.id.edit_tilt_sb);
        tSeekBar.setOnSeekBarChangeListener(this);

        bSeekBar = (SeekBar)view.findViewById(R.id.edit_blink_sb);
        bSeekBar.setOnSeekBarChangeListener(this);

        backBtn = (FloatingActionButton) view.findViewById(R.id.edit_back_fab);

        stepPrevBtn = (FloatingActionButton)view.findViewById(R.id.edit_step_prev);
        stepPrevBtn.setOnClickListener(this);

        stepNextBtn = (FloatingActionButton)view.findViewById(R.id.edit_step_next);
        stepNextBtn.setOnClickListener(this);

        delayPrevBtn = (FloatingActionButton)view.findViewById(R.id.edit_delay_prev);
        delayPrevBtn.setOnClickListener(this);

        delayNextBtn = (FloatingActionButton)view.findViewById(R.id.edit_delay_next);
        delayNextBtn.setOnClickListener(this);

        positiveBtn = (Button)view.findViewById(R.id.edit_positive_btn);
        positiveBtn.setOnClickListener(this);

        negativeBtn = (Button)view.findViewById(R.id.edit_negative_btn);
        negativeBtn.setOnClickListener(this);

        addStpBtn = (Button)view.findViewById(R.id.edit_add_step_btn);
        addStpBtn.setOnClickListener(this);

        stepEd = (EditText)view.findViewById(R.id.edit_step_et);
        delayEd = (EditText)view.findViewById(R.id.edit_delay_et);

        if(internalMode == 1){
            strtext = getArguments().getString("pgmName") + (PgmCollection.totalPgmItem.size()+1);
            positiveBtn.setText("Save");
            stepEd.setText(step+"");
            delayEd.setText(delay+"");
            mPgmItem = new PgmItem(PgmCollection.totalPgmItem.size() + 1 +"");
            mStepItem = new StepItem();
            mStepItem.setPgmName(mPgmItem.getPgm());
            mPgmItem.stepList.add(mStepItem);
            mStepItem.setStpName(mPgmItem.stepList.size()+"");
            stepSize = step = mPgmItem.stepList.size();
            stepEd.setText(step+"");
            currentStepItem = mPgmItem.stepList.get(step-1);
        }else if (internalMode == 2){
            strtext = getArguments().getString("pgmName");
            currentItemPos = Integer.parseInt(getArguments().getString("pgmPos"));
            unbindedPgmItem = new PgmItem(currentItemPos+"");
            positiveBtn.setText("Update");
            step = 1;
            stepEd.setText(step+"");
            mPgmItem = new PgmItem(currentItemPos+"");
            mPgmItem = PgmCollection.totalPgmItem.get(currentItemPos-1);
            stepSize = mPgmItem.stepList.size();
            holdAdjustmentValues();
            holdTemporaryPgm();
            setAdjustmentValues();
        }

        if(strtext != "" || strtext != null){
            pgmName.setText(strtext);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Attention!")
                        .setMessage("Do you want to save the Program?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(internalMode == 1){
                                    saveProgram();
                                }else if(internalMode == 2){
                                    updateProgram();
                                }
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelSaving();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        stepEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String holdStepText = stepEd.getText().toString().trim();
                if(holdStepText.isEmpty()){
                    step = 1;
                    stepEd.setText(step+"");
                }else if(Integer.parseInt(holdStepText) > stepSize){
                    step = stepSize;
                    stepEd.setText(step+"");
                }else {
                    step = Integer.parseInt(stepEd.getText().toString());
                }
                setAdjustmentValues();
            }
        });

        delayEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String holdDelayText = delayEd.getText().toString().trim();
                if(holdDelayText.isEmpty()){
                    delay = 0;
                    delayEd.setText(delay+"");
                }else if(delay > 20){
                    delay = 20;
                    delayEd.setText(delay+"");
                }else {
                    delay = Integer.parseInt(delayEd.getText().toString());
                }
                currentStepItem.setTime(delay);

                //mPgmItem.stepList.set(step-1, currentStepItem);
            }
        });
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Protocol.cDeviceProt.postDelayedSendToModule.removeCallbacksAndMessages(null);
        byte[] movData = new byte[3];
        byte command = 0x02;
        byte[] data;

        switch (seekBar.getId()){
            case R.id.edit_pan_sb:
                pVal = pSeekBar.getProgress();
                movData = Protocol.cDeviceProt.setData(pVal, tVal, bVal);
                data = new byte[]{(byte) 0x01, (byte) 0x01, movData[0], movData[1], movData[2],(byte) 0x01};
                Protocol.cDeviceProt.transferDataWithDelay(command, data);
                currentStepItem.setPan(pVal);
                //mPgmItem.stepList.set(step-1, currentStepItem);
                break;
            case R.id.edit_tilt_sb:
                tVal = tSeekBar.getProgress();
                movData = Protocol.cDeviceProt.setData(pVal, tVal, bVal);
                data = new byte[]{(byte) 0x01, (byte) 0x01, movData[0], movData[1], movData[2],(byte) 0x01};
                Protocol.cDeviceProt.transferDataWithDelay(command, data);
                currentStepItem.setTilt(tVal);
                //mPgmItem.stepList.set(step-1, currentStepItem);
                break;
            case R.id.edit_blink_sb:
                bVal = bSeekBar.getProgress();
                movData = Protocol.cDeviceProt.setData(pVal, tVal, bVal);
                data = new byte[]{(byte) 0x01, (byte) 0x01, movData[0], movData[1], movData[2],(byte) 0x01};
                Protocol.cDeviceProt.transferDataWithDelay(command, data);
                currentStepItem.setBlink(bVal);
                //mPgmItem.stepList.set(step-1, currentStepItem);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //deviceProtocol.stopChannel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //deviceProtocol.startChannel();
    }

    @Override
    public void onStart() {
        super.onStart();
        //deviceProtocol.startChannel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //deviceProtocol.stopChannel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //deviceProtocol.stopChannel();
    }

    @Override
    public void onResume() {
        super.onResume();
        //deviceProtocol.startChannel();
    }

    @Override
    public void onPause() {
        super.onPause();
        //deviceProtocol.stopChannel();
    }

    private void holdAdjustmentValues(){
        for (StepItem item: mPgmItem.stepList) {
            holdPval.add(item.getPan());
            holdTval.add(item.getTilt());
            holdBval.add(item.getBlink());
            holdDelayVal.add(item.getTime());
            holdStepName.add(item.getStpName());
        }
    }

    private void holdTemporaryPgm(){
        for(int i = 0; i < stepSize; i++){
            StepItem newStepItem = new StepItem();
            newStepItem.setPan(holdPval.get(i));
            newStepItem.setTilt(holdTval.get(i));
            newStepItem.setBlink(holdBval.get(i));
            newStepItem.setTime(holdDelayVal.get(i));
            newStepItem.setStpName(holdStepName.get(i));
            newStepItem.setPgmName(currentItemPos+"");
            unbindedPgmItem.stepList.add(newStepItem);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_step_prev:
                if(step > 1)
                    step--;
                    stepEd.setText(step+"");
                break;
            case R.id.edit_step_next:
                if(step < 99)
                    step++;
                    stepEd.setText(step+"");
                break;
            case R.id.edit_delay_prev:
                if(delay > 0)
                    delay--;
                    delayEd.setText(delay+"");
                break;
            case R.id.edit_delay_next:
                if(delay < 20)
                    delay++;
                    delayEd.setText(delay+"");
                break;
            case R.id.edit_positive_btn:
                if(internalMode == 1){
                    saveProgram();
                }else if(internalMode == 2){
                    updateProgram();
                }
                break;
            case R.id.edit_negative_btn:
                if(internalMode == 1){
                    deleteStep();
                }else if(internalMode == 2){
                    deleteStepInEditMode();
                }
                break;
            case R.id.edit_add_step_btn:
                if(internalMode == 1){
                    createNewStep();
                }else if(internalMode == 2){
                    createNewStepEdit();
                }
                break;
        }
    }

    private void saveProgram(){
        PgmCollection.totalPgmItem.add(mPgmItem);
        returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        //save to device command here
        getActivity().finish();
        Toast.makeText(getContext(), "Program Saved", Toast.LENGTH_SHORT).show();
    }

    private void updateProgram(){
        //PgmCollection.totalPgmItem.set(currentItemPos-1, unbindedPgmItem);
        PgmItem toUpdatePgmItem = PgmCollection.totalPgmItem.get(currentItemPos-1);
        toUpdatePgmItem.stepList = unbindedPgmItem.stepList;

        returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        //update to devic command here
        getActivity().finish();
        Toast.makeText(getContext(), "Program Updated", Toast.LENGTH_SHORT).show();
    }

    private void cancelSaving(){
//        byte command = 0x02;
//        byte[] data = new byte[]{(byte) 0x01, (byte) 0x01, (byte)0, (byte)0, (byte)0,(byte) 0x01};
//        Protocol.cDeviceProt.transferDataWithDelay(command, data);
        returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
        getActivity().finish();
        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void createNewStep(){
        mStepItem = new StepItem();
        mStepItem.setPgmName(mPgmItem.getPgm());
        mPgmItem.stepList.add(mStepItem);
        mStepItem.setStpName(mPgmItem.stepList.size()+"");
        stepSize = step = mPgmItem.stepList.size();
        stepEd.setText(step+"");
        currentStepItem = mPgmItem.stepList.get(step-1);
//        pVal = tVal = bVal = delay = 0;
//        pSeekBar.setProgress(pVal);
//        tSeekBar.setProgress(tVal);
//        bSeekBar.setProgress(bVal);
//        delayEd.setText(delay+"");
    }

    private void createNewStepEdit(){
        mStepItem = new StepItem();
        mStepItem.setPgmName(unbindedPgmItem.getPgm());
        unbindedPgmItem.stepList.add(mStepItem);
        mStepItem.setStpName(unbindedPgmItem.stepList.size()+"");
        stepSize = step = unbindedPgmItem.stepList.size();
        stepEd.setText(step+"");
        currentStepItem = unbindedPgmItem.stepList.get(step-1);
    }

    private void deleteStep(){
        if(stepSize > 1){
            mPgmItem.stepList.remove(step-1);
            stepSize = mPgmItem.stepList.size();
            for(int i = step-1; i < stepSize; i++){
                StepItem holdStep = mPgmItem.stepList.get(i);
                holdStep.setStpName((i+1)+"");
                mPgmItem.stepList.set(i, holdStep);
            }
            stepEd.setText(step+"");
            Toast.makeText(getContext(), "Step Deleted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "You only have one step on this program", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStepInEditMode(){
        if(stepSize > 1){
            unbindedPgmItem.stepList.remove(step-1);
            stepSize = unbindedPgmItem.stepList.size();
            for(int i = step-1; i < stepSize; i++){
                StepItem holdStep = unbindedPgmItem.stepList.get(i);
                holdStep.setStpName((i+1)+"");
                unbindedPgmItem.stepList.set(i, holdStep);
            }
            stepEd.setText(step+"");
            Toast.makeText(getContext(), "Step Deleted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "You only have one step on this program", Toast.LENGTH_SHORT).show();
        }
    }

    private void temporaryDeleteStep(){
        holdPval.remove(step -1);
        holdTval.remove(step -1);
        holdBval.remove(step -1);
    }

    private void setAdjustmentValues(){
        if(internalMode == 1){
            currentStepItem = mPgmItem.stepList.get(step-1);
        }else if(internalMode == 2){
            currentStepItem = unbindedPgmItem.stepList.get(step-1);
        }

        pVal = currentStepItem.getPan();
        tVal = currentStepItem.getTilt();
        bVal = currentStepItem.getBlink();
        delay = currentStepItem.getTime();

        pSeekBar.setProgress(pVal);
        tSeekBar.setProgress(tVal);
        bSeekBar.setProgress(bVal);
        delayEd.setText(delay+"");
    }

    @Override
    public void onStop() {
        super.onStop();
        byte command = 0x02;
        byte[] data = new byte[]{(byte) 0x01, (byte) 0x01, (byte)0, (byte)0, (byte)0,(byte) 0x01};
        Protocol.cDeviceProt.transferDataWithDelay(command, data);
    }

    //    public void openDialog(){
//        ConfirmationDialog dialog = new ConfirmationDialog();
//        dialog.setTargetFragment(this, 0);
//        dialog.show(getFragmentManager(), "confirmationDialog");
//    }
}
