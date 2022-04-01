package com.android.model;

public class DataModel {
    private int number;
    private boolean isSelect;
    private boolean isRed;
    private boolean isGray;

    public DataModel(int number, boolean isSelect) {
        this.number = number;
        this.isSelect = isSelect;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public boolean isGray() {
        return isGray;
    }

    public void setGray(boolean gray) {
        isGray = gray;
    }
}
