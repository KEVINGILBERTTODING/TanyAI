package com.example.tanyai.core.models;

public class UserSharedModel {
    private boolean isFirstTime;

    public UserSharedModel(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }
}
