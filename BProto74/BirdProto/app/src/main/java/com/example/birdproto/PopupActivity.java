package com.example.birdproto;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.birdproto.common.DeviceProtocol;
import com.example.birdproto.stepitem.StepItem;

import java.util.ArrayList;

public class PopupActivity extends FragmentActivity{
    private boolean isBackDisabled = false;
    PopupImportFragment fragobjImport;
    PopupSaveFragment fragobjSave;
    PopupSeqProgSelectFragment fragobjPgmSelect;

    Bundle bundle;

    //DeviceProtocol mDProtocol = new DeviceProtocol();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
//        Intent intent = getIntent();
//
//        String a = intent.getIntExtra()

        Intent intent = getIntent();
        int mode = intent.getIntExtra("Mode", 0);
        String pgmName = intent.getStringExtra("PgmName");
        String seqName = intent.getStringExtra("SequenceName");
        ArrayList<StepItem> pgmSteps = (ArrayList<StepItem>) intent.getSerializableExtra("Steps");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

// modes: 0(default) =

        WindowManager.LayoutParams params = getWindow().getAttributes();
        if(mode == 2 || mode == 4){
            getWindow().setLayout(width, (height/3)+40);
            params.gravity = Gravity.CENTER;
        }else if(mode == 3 || mode == 5 || mode == 6 || mode == 7){
            getWindow().setLayout(width, (height/2)+(height/4));
            params.gravity = Gravity.BOTTOM;
        } else {
            getWindow().setLayout(width, (height/2)+(height/3)+50);
            params.gravity = Gravity.BOTTOM;
        }


        getWindow().setAttributes(params);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(mode == 1){
            bundle = new Bundle();
            bundle.putInt("Mode", 2);
            bundle.putString("pgmName", "Program - " + pgmName);
            bundle.putString("pgmPos", pgmName);
            bundle.putSerializable("stepList", pgmSteps);
            PopupEditFragment fragobjEdit = new PopupEditFragment();
            fragobjEdit.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjEdit).commit();
        }else if(mode == 2){
            String txPgmName = intent.getStringExtra("PgmName");
            int txPgmDuration = intent.getIntExtra("PgmDuration", 0);

            bundle = new Bundle();
            bundle.putInt("fragMode", 1);
            bundle.putString("pgmName", txPgmName);
            bundle.putInt("pgmDuration", txPgmDuration);
            fragobjSave = new PopupSaveFragment();
            fragobjSave.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjSave).commit();
        }else if(mode == 3){
            bundle = new Bundle();
            bundle.putInt("fragMode", 1);
            fragobjImport = new PopupImportFragment();
            fragobjImport.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjImport).commit();
        }else if(mode == 4){


            bundle = new Bundle();
            bundle.putInt("fragMode", 2);
            fragobjSave = new PopupSaveFragment();
            fragobjSave.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjSave).commit();
        }else if(mode == 5){
            bundle = new Bundle();
            bundle.putInt("fragMode", 2);
            fragobjImport = new PopupImportFragment();
            fragobjImport.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjImport).commit();
        }else if(mode == 6){
            ft.replace(R.id.popup_container, new PopupFragmentSequence()).commit();
        }else if(mode == 7){
            bundle = new Bundle();
            bundle.putString("SequenceName", seqName);
            fragobjPgmSelect = new PopupSeqProgSelectFragment();
            fragobjPgmSelect.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjPgmSelect).commit();
        }else{
            bundle = new Bundle();
            bundle.putInt("Mode", 1);
            bundle.putString("pgmName", "New: Program - ");
            bundle.putSerializable("stepList", null);
            PopupEditFragment fragobjAdd = new PopupEditFragment();
            fragobjAdd.setArguments(bundle);
            ft.replace(R.id.popup_container, fragobjAdd).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(isBackDisabled){
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mDProtocol.startChannel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mDProtocol.stopChannel();
    }
}
