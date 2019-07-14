package com.example.birdproto.setupitem;

import com.example.birdproto.sequenceitem.SequenceItem;

import java.util.ArrayList;

public class ScheduledSeqItem {
    public SequenceItem sequenceItem;

    public ArrayList<Day> listOfDays = new ArrayList<>();

    public SequenceItem getSequenceItem() {
        return sequenceItem;
    }

    public void setSequenceItem(SequenceItem sequenceItem) {
        this.sequenceItem = sequenceItem;
    }

    public ArrayList<Day> getListOfDays() {
        return listOfDays;
    }

    public void setListOfDays(ArrayList<Day> listOfDays) {
        this.listOfDays = listOfDays;
    }
}
