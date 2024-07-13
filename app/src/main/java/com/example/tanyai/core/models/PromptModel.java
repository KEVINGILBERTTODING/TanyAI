package com.example.tanyai.core.models;

public class PromptModel {
    private String text;
    private boolean isMe;
    private String timeStamp;

    public PromptModel(String text, boolean isMe, String timeStamp) {
        this.text = text;
        this.isMe = isMe;
        this.timeStamp = timeStamp;
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
}
