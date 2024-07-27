package com.example.tanyai.core.models;

import android.graphics.Bitmap;
import android.net.Uri;

public class PromptModel {
    private String text;
    private boolean isMe;
    private String timeStamp;
    private Bitmap bitmap;

    public PromptModel(String text, boolean isMe, String timeStamp, Bitmap bitmap) {
        this.text = text;
        this.isMe = isMe;
        this.timeStamp = timeStamp;
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
