package com.example.birdproto.scheduleitem;

public class SchdItem {
    private String schdProg;
    private int schdTime = 0;
    boolean canAdd = false;

    public SchdItem(String schd) {
        this.schdProg = schd;
    }

    public String getSchd() {
        return schdProg;
    }

    public void setSchd(String schd) {
        this.schdProg = schd;
    }

    public int getSchdTime() {
        return schdTime;
    }

    public void setSchdTime(int schdTime) {
        this.schdTime = schdTime;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public SchdItem(String schdProg, int schdTime) {
        this.schdProg = schdProg;
        this.schdTime = schdTime;
    }
}
