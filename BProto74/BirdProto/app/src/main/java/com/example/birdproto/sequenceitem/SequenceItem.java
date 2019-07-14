package com.example.birdproto.sequenceitem;

import com.example.birdproto.programitem.PgmItem;
import com.example.birdproto.setupitem.Day;

import java.util.ArrayList;

public class SequenceItem {
    public String name;
    public int sHour;
    public int sMinute;
    public int eHour;
    public int eMinute;

    public int getsHour() {
        return sHour;
    }

    public void setsHour(int sHour) {
        this.sHour = sHour;
    }

    public int getsMinute() {
        return sMinute;
    }

    public void setsMinute(int sMinute) {
        this.sMinute = sMinute;
    }

    public int geteHour() {
        return eHour;
    }

    public void seteHour(int eHour) {
        this.eHour = eHour;
    }

    public int geteMinute() {
        return eMinute;
    }

    public void seteMinute(int eMinute) {
        this.eMinute = eMinute;
    }

    public ArrayList<PgmItem> getPgmList() {
        return pgmList;
    }

    public void setPgmList(ArrayList<PgmItem> pgmList) {
        this.pgmList = pgmList;
    }

    public ArrayList<PgmItem> pgmList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return "Sequence" + name;
    }
}
