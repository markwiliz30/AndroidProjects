package com.example.birdproto.programitem;

import com.example.birdproto.stepitem.StepItem;

import java.util.ArrayList;

public class PgmItem {
    public String pgm;
    public int pgmDuration = 0;
    private boolean isRunning = false;
    private boolean isSelected = false, isIndicatorVisible = true;

    public ArrayList<StepItem> getStepList() {
        return stepList;
    }

    public void setStepList(ArrayList<StepItem> stepList) {
        this.stepList = stepList;
    }

    public ArrayList<StepItem> stepList = new ArrayList<>();

    public boolean isIndicatorVisible() {
        return isIndicatorVisible;
    }

    public void setIndicatorVisible(boolean indicatorVisible) {
        isIndicatorVisible = indicatorVisible;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getPgmDuration() {
        return pgmDuration;
    }

    public void setPgmDuration(int pgmDuration) {
        this.pgmDuration = pgmDuration;
    }

    public PgmItem(String pgm) {
        this.pgm = pgm;
    }

    public String getPgm() {
        return pgm;
    }

    public void setPgm(String pgm) {
        this.pgm = pgm;
    }
}
