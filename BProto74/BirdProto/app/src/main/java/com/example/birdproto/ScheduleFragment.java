package com.example.birdproto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.birdproto.common.SequenceCollection;
import com.example.birdproto.common.SetupCollection;
import com.example.birdproto.scheduleitem.SchdAdapter;
import com.example.birdproto.scheduleitem.SchdItem;
import com.example.birdproto.sequenceitem.SequenceEnum;
import com.example.birdproto.sequenceitem.SequenceItem;
import com.example.birdproto.setupitem.Day;
import com.example.birdproto.setupitem.ScheduledSeqItem;
import com.example.birdproto.setupitem.SetupEnum;
import com.example.birdproto.setupitem.SetupItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class ScheduleFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinSetup, spinSequence;
    Button startDateBtn, endDateBtn;
    String startDateTxt, endDateTxt;
    boolean isStartDatePressed = true;
    GridLayout schdGrid;
    SetupEnum currentSetup;
    SequenceEnum currentSequence;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    int startDateMonth = 0,startDateDay = 0,endDateMonth = 0,endDateDay = 0;
    SetupItem setupItem, currentSetupItem;
    int setupPos = 0, sequencePos = 0, setupSize = 0, sequenceSize = 0, scheduledSeqPos = 0;
    ArrayList<SetupItem> mSetupItems = new ArrayList<>(4);
    SequenceItem mSeqItem, currentSeqItem = new SequenceItem();
    ScheduledSeqItem currentScheduledSeqItem = new ScheduledSeqItem();
    ArrayList<SequenceItem> currentSequenceItems = new ArrayList<>(10);
    FloatingActionButton addSeq, removeSeq;
    ArrayAdapter<SequenceItem> adapterSeq;
    boolean inTheScheduledList = false;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text;
        switch (parent.getId()){
            case R.id.spin_sequence:
//                text = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                sequencePos = position;
                currentSeqItem = currentSequenceItems.get(sequencePos);
                assignCurrentScheduledItem();
                if(currentScheduledSeqItem.listOfDays.size() == 7){
                    displayDaysIncludedAll(schdGrid);
                }else {
                    displayDaysIncluded(schdGrid);
                }
                break;
            case R.id.spn_setup_select:
//                text = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                setupPos = position;
                sequencePos = position;
                displaySelectedSetupDetails();
                assignCurrentScheduledItem();
                if(currentScheduledSeqItem.listOfDays.size() == 7){
                    displayDaysIncludedAll(schdGrid);
                }else {
                    displayDaysIncluded(schdGrid);
                }
                break;
        }
    }

    private void displaySelectedSetupDetails(){
        currentSetupItem = mSetupItems.get(setupPos);
        //currentSeqItem = currentSequenceItems.get(sequencePos);
        //currentSequenceItems = currentSetupItem.listOfSequence;
        startDateMonth = currentSetupItem.startDateMonth;
        startDateDay = currentSetupItem.startDateDay;
        endDateMonth = currentSetupItem.endDateMonth;
        endDateDay = currentSetupItem.endDateDay;
        startDateTxt = convertIntMothToString(startDateMonth)+" "+startDateDay;
        endDateTxt = convertIntMothToString(endDateMonth)+" "+endDateDay;
        if(startDateMonth != -1 && startDateDay != -1){
            startDateBtn.setText(startDateTxt);
        }else {
            startDateBtn.setText("Select");
        }

        if(endDateMonth != -1 && endDateDay != -1){
            endDateBtn.setText(endDateTxt);
        }else {
            endDateBtn.setText("Select");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = new Intent(getContext(), PopupActivity.class);

        switch (id){
            case R.id.act_sched_set:
                i.putExtra("Mode", 6);
                startActivity(i);
                break;
            case R.id.act_schd_import:
                i.putExtra("Mode", 5);
                startActivity(i);
                break;
            case R.id.act_schd_save:
                i.putExtra("Mode", 4);
                startActivity(i);
                break;
            case R.id.act_schd_upload:
                Toast.makeText(getContext(), "Upload Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.act_schd_add_setup:
                if(setupSize < 4){
                    addSetup();
                    Toast.makeText(getContext(), "New setup successfully created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "You can only create maximum of 4 setups", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_schd_delete_setup:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Attention!")
                        .setMessage("Do you want to delete the selected setup?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(setupSize != 1){
                                    deleteSetup();
                                    assignCurrentScheduledItem();
                                    displayDaysIncluded(schdGrid);
                                }else{
                                    Toast.makeText(getContext(), "Deletion of selected setup failed because you only have one setup", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteSetup(){
        mSetupItems.remove(setupPos);
        setupSize = mSetupItems.size();
        for(int x = setupPos; x < setupSize; x++){
            SetupItem holdSetup = mSetupItems.get(x);
            holdSetup.setSetupName((x+1)+"");
            mSetupItems.set(x, holdSetup);
        }
        spinSetup.setSelection(0);
        setupPos = 0;
        displaySelectedSetupDetails();
        Toast.makeText(getContext(), "Selected Setup Successfully Deleted", Toast.LENGTH_SHORT).show();
    }
    private void addSetup(){
        setupItem = new SetupItem();
        setupItem.setupName = (mSetupItems.size()+1)+"";
        setupItem.startDateMonth = -1;
        setupItem.startDateDay = -1;
        setupItem.endDateMonth = -1;
        setupItem.endDateDay = -1;

        mSetupItems.add(setupItem);
        setupSize = mSetupItems.size();
        startDateBtn.setText("Select");
        endDateBtn.setText("Select");
        setupPos = setupSize-1;
        spinSetup.setSelection(setupPos);

        displaySelectedSetupDetails();
        assignCurrentScheduledItem();
        displayDaysIncluded(schdGrid);

        Toast.makeText(getContext(), "New setup successfully created.", Toast.LENGTH_SHORT).show();
    }

    private void setSpinSequenceAdatpter(){
        adapterSeq = new ArrayAdapter<SequenceItem>(getContext(), R.layout.support_simple_spinner_dropdown_item, currentSequenceItems);
        adapterSeq.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinSequence.setAdapter(adapterSeq);
    }

    private void addNewSequence(){
            mSeqItem = new SequenceItem();
            mSeqItem.setName((currentSequenceItems.size()+1)+"");
            currentSequenceItems.add(mSeqItem);
            sequenceSize = currentSequenceItems.size();
            spinSequence.setSelection(sequenceSize-1);
    }

    private void deleteSequence(){
        SequenceItem toDeleteSeqItem = currentSequenceItems.remove(sequencePos);
        removeSeqInSelectedSetup(toDeleteSeqItem);
        sequenceSize = currentSequenceItems.size();
        for(int x = sequencePos; x < sequenceSize; x++){
            SequenceItem holdSequence = currentSequenceItems.get(x);
            holdSequence.setName((x+1)+"");
            currentSequenceItems.set(x, holdSequence);
        }
        currentSeqItem = currentSequenceItems.get(0);
        spinSequence.setSelection(0);
        //setCurrentScheduledSeqItem();
        assignCurrentScheduledItem();
        displayDaysIncluded(schdGrid);
    }

    private void removeSeqInSelectedSetup(SequenceItem mToDeleteSeqItem){
        for (ScheduledSeqItem item : currentSetupItem.listOfScheduledSeq) {
            if(item.sequenceItem.equals(mToDeleteSeqItem)){
                currentSetupItem.listOfScheduledSeq.remove(item);
                break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        currentSequenceItems = SequenceCollection.sequenceList;

        schdGrid = (GridLayout)view.findViewById(R.id.schd_grid);

        spinSetup = (Spinner)view.findViewById(R.id.spn_setup_select);
        spinSequence = (Spinner)view.findViewById(R.id.spin_sequence);

        startDateBtn = (Button)view.findViewById(R.id.schd_start_date_btn);
        endDateBtn = (Button)view.findViewById(R.id.schd_end_date_btn);

        addSeq = (FloatingActionButton)view.findViewById(R.id.add_sequence_fab);
        removeSeq = (FloatingActionButton)view.findViewById(R.id.remove_sequence_fab);

        mSetupItems = SetupCollection.setupList;


        if(SetupCollection.setupList.size() < 1){
            addSetup();
            addNewSequence();
        }

        setSpinSequenceAdatpter();
//        ArrayAdapter<CharSequence> adapterSeq = ArrayAdapter.createFromResource(getContext(), R.array.sequence, R.layout.support_simple_spinner_dropdown_item);
//        adapterSeq.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //--old--//ArrayAdapter<SetupItem> adapterSetup = ArrayAdapter.createFromResource(getContext(), SetupCollection.setupList, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<SetupItem> adapterSetup = new ArrayAdapter<SetupItem>(getContext(), R.layout.support_simple_spinner_dropdown_item, mSetupItems);
        adapterSetup.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinSetup.setAdapter(adapterSetup);
        spinSetup.setOnItemSelectedListener(this);

        spinSequence.setOnItemSelectedListener(this);

        addSeq.setOnClickListener(this);
        removeSeq.setOnClickListener(this);
        
        //setSingleEvent(schdGrid);
        setToggleEvent(schdGrid);

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar sCal = Calendar.getInstance();
                int sYear = sCal.get(Calendar.YEAR);
                int sMonth = sCal.get(Calendar.MONTH);
                int sDay = sCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDateMonth = monthOfYear;
                        startDateDay = dayOfMonth;
                        startDateTxt = convertIntMothToString(startDateMonth)+" "+startDateDay;
                        startDateBtn.setText(startDateTxt);
                        currentSetupItem.startDateMonth = startDateMonth;
                        currentSetupItem.startDateDay = startDateDay;
                    }
                }, sYear, sMonth, sDay);

                dialog.getDatePicker().findViewById(getResources().getIdentifier("year","id","android")).setVisibility(View.GONE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                isStartDatePressed = true;
                dialog.show();
            }
        });
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar eCal = Calendar.getInstance();
                int eYear = eCal.get(Calendar.YEAR);
                int eMonth = eCal.get(Calendar.MONTH);
                int eDay = eCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDateMonth = monthOfYear;
                        endDateDay = dayOfMonth;
                        endDateTxt = convertIntMothToString(endDateMonth)+" "+endDateDay;
                        endDateBtn.setText(endDateTxt);
                        currentSetupItem.endDateMonth = endDateMonth;
                        currentSetupItem.endDateDay = endDateDay;
                    }
                }, eYear, eMonth, eDay);

                dialog.getDatePicker().findViewById(getResources().getIdentifier("year","id","android")).setVisibility(View.GONE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                isStartDatePressed = false;
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(isStartDatePressed){
                    startDateTxt = convertIntMothToString(month)+" "+dayOfMonth;
                    startDateBtn.setText(startDateTxt);
                }else {
                    endDateTxt = convertIntMothToString(month)+" "+dayOfMonth;
                    endDateBtn.setText(endDateTxt);
                }
            }
        };
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setToggleEvent(final GridLayout schdGrid) {
        for (int i = 0;i<schdGrid.getChildCount();i++){
            final CardView cardView = (CardView)schdGrid.getChildAt(i);
            final int finalI = i;
            final int itemSize = schdGrid.getChildCount();
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getCardBackgroundColor().getDefaultColor() == -1){
                        //change background color
                        if(finalI == 7){
                            eventIncludeAllDays();
                            displayDaysIncludedAll(schdGrid);
                        }else {
                            eventIncludeDay(finalI);
                            displayDaysIncluded(schdGrid);
                        }

                        if(currentScheduledSeqItem.listOfDays.size() == 7){
                            displayDaysIncludedAll(schdGrid);
                        }
                    }else {
                        //change background color
                        if(finalI == 7){
                            eventExcludeAllDays();
                            displayDaysExcludedAll(schdGrid);
                        }
                        else {
                            eventExcludeDay(finalI);
                            displayDaysIncluded(schdGrid);
                        }
                    }
                }
            });
        }
    }

    private void displayDaysIncluded(GridLayout lSchdGrid){
        CardView lCardView;

        for(int i = 0;i<lSchdGrid.getChildCount();i++){
            lCardView = (CardView)lSchdGrid.getChildAt(i);
            lCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        for (Day x: currentScheduledSeqItem.listOfDays) {
            int y = x.ordinal();
            lCardView = (CardView)lSchdGrid.getChildAt(y);
            lCardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
        }
    }

    private void displayDaysIncludedAll(GridLayout lSchdGrid){
        CardView lCardView;

        for(int i = 0;i<lSchdGrid.getChildCount();i++){
            lCardView = (CardView)lSchdGrid.getChildAt(i);
            lCardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
        }
    }

    private void displayDaysExcludedAll(GridLayout lSchdGrid){
        CardView lCardView;

        for(int i = 0;i<lSchdGrid.getChildCount();i++){
            lCardView = (CardView)lSchdGrid.getChildAt(i);
            lCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void eventIncludeAllDays(){
        setCurrentScheduledSeqItem();

        for(int i = 0;i<7;i++){
            Day x = Day.values()[i];

            if(!currentScheduledSeqItem.listOfDays.contains(x)){
                currentScheduledSeqItem.listOfDays.add(x);
            }
        }
        Toast.makeText(getContext(), "All days are included", Toast.LENGTH_SHORT).show();
    }

    private void eventIncludeDay(int dayPos){
        Day x = Day.values()[dayPos];

        setCurrentScheduledSeqItem();

        if(!currentScheduledSeqItem.listOfDays.contains(x)){
            currentScheduledSeqItem.listOfDays.add(x);
            Toast.makeText(getContext(), x + " is included in the sequence", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), x + " is already included in the sequence", Toast.LENGTH_SHORT).show();
        }
    }

    private void eventExcludeAllDays(){
        assignCurrentScheduledItem();

        for(int i = 0;i<7;i++){
            Day x = Day.values()[i];

            if(currentScheduledSeqItem.listOfDays.contains(x)){
                currentScheduledSeqItem.listOfDays.remove(x);
            }
        }

        if(currentScheduledSeqItem.listOfDays.size() == 0){
            currentSetupItem.listOfScheduledSeq.remove(currentScheduledSeqItem);
        }
        Toast.makeText(getContext(), "All days are excluded", Toast.LENGTH_SHORT).show();
    }

    private void eventExcludeDay(int dayPos){
        Day x = Day.values()[dayPos];

        //setCurrentScheduledSeqItem();
        assignCurrentScheduledItem();

        if(currentScheduledSeqItem.listOfDays.contains(x)){
            currentScheduledSeqItem.listOfDays.remove(x);
            Toast.makeText(getContext(), x + " is excluded in the sequence", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),x + " is already excluded in the sequence", Toast.LENGTH_SHORT).show();
        }

        if(currentScheduledSeqItem.listOfDays.size() == 0){
            currentSetupItem.listOfScheduledSeq.remove(currentScheduledSeqItem);
        }
    }

    private void assignCurrentScheduledItem(){
        if(currentSetupItem.listOfScheduledSeq.size() == 0){
            currentScheduledSeqItem = new ScheduledSeqItem();
            currentScheduledSeqItem.sequenceItem = currentSeqItem;
            inTheScheduledList = false;
        }else {
            for (ScheduledSeqItem item : currentSetupItem.listOfScheduledSeq) {
                if(item.sequenceItem.equals(currentSeqItem)){
                    currentScheduledSeqItem = new ScheduledSeqItem();
                    currentScheduledSeqItem = item;
                    inTheScheduledList = true;
                    break;
                }
            }

            try {
                if(!currentScheduledSeqItem.sequenceItem.equals(currentSeqItem)){
                    currentScheduledSeqItem = new ScheduledSeqItem();
                    currentScheduledSeqItem.sequenceItem = currentSeqItem;
                    inTheScheduledList = false;
                }
            }catch (Exception e){

            }
        }
    }

    private void setCurrentScheduledSeqItem(){
        if(!inTheScheduledList){
            inTheScheduledList = true;
            currentSetupItem.listOfScheduledSeq.add(currentScheduledSeqItem);
        }
    }

    private void setSingleEvent(GridLayout schdGrid) {
        //loop all child item of schdGrid
        for (int i = 0;i<schdGrid.getChildCount();i++){
            CardView cardView = (CardView)schdGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clicked at index" + finalI, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String convertIntMothToString(int intMonth){
        switch (intMonth){
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }

        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_sequence_fab:
                if(currentSequenceItems.size() < 10){
                    addNewSequence();
                    Toast.makeText(getContext(), "New sequence successfully created.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Creating new sequence failed. You've reach the maximum capacity of sequence you can create.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.remove_sequence_fab:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Attention!")
                        .setMessage("Do you want to delete the selected sequence?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(currentSequenceItems.size() > 1){
                                    deleteSequence();
                                    Toast.makeText(getContext(), "Selected sequence successfully deleted", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(), "Deleting sequence failed. A setup must contain atleast 1 sequence", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
    }
}
