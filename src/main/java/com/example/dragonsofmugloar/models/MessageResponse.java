package com.example.dragonsofmugloar.models;

public class MessageResponse {

    private boolean success;
    private long lives;
    private long gold;
    private long score;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public long getLives() {
        return lives;
    }

    public long getGold() {
        return gold;
    }

    public long getScore() {
        return score;
    }

    public String getMessage() {
        return message;
    }
}
