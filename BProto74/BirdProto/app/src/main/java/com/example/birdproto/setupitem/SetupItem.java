package com.example.birdproto.setupitem;

import com.example.birdproto.sequenceitem.SequenceItem;

import java.util.ArrayList;

public class SetupItem {
    public String setupName;
    public int startDateMonth;
    public int startDateDay;
    public int endDateMonth;
    public int endDateDay;
    public ArrayList<ScheduledSeqItem> listOfScheduledSeq = new ArrayList<>();

    public ArrayList<ScheduledSeqItem> getListOfSequence() {
        return listOfScheduledSeq;
    }

    public void setListOfSequence(ArrayList<ScheduledSeqItem> listOfSequence) {
        this.listOfScheduledSeq = listOfSequence;
    }

    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName;
    }

    public int getStartDateMonth() {
        return startDateMonth;
    }

    public void setStartDateMonth(int startDateMonth) {
        this.startDateMonth = startDateMonth;
    }

    public int getStartDateDay() {
        return startDateDay;
    }

    public void setStartDateDay(int startDateDay) {
        this.startDateDay = startDateDay;
    }

    public int getEndDateMonth() {
        return endDateMonth;
    }

    public void setEndDateMonth(int endDateMonth) {
        this.endDateMonth = endDateMonth;
    }

    public int getEndDateDay() {
        return endDateDay;
    }

    public void setEndDateDay(int endDateDay) {
        this.endDateDay = endDateDay;
    }

    @Override
    public String toString() {
        return "Setup - " + setupName;
    }
}