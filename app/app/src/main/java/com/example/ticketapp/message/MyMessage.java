package com.example.ticketapp.message;

import java.util.UUID;

public class MyMessage {

    String id;
    String senterId;
    String recieverId;
    String date;
    String message;

    public MyMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenterId() {
        return senterId;
    }

    public void setSenterId(String senterId) {
        this.senterId = senterId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "senterId='" + senterId + '\'' +
                ", recieverId='" + recieverId + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
