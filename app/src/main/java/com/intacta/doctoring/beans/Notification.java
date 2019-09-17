package com.intacta.doctoring.beans;

public class Notification {
    private String message;

    public Notification(String message, String key, String data, boolean read) {
        this.message = message;
        this.key = key;
        this.data = data;
        this.read = read;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;
    private boolean read;

    public Notification(String message) {
        this.message = message;

    }

    public Notification() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
