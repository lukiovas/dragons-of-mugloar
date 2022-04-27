package com.example.dragonsofmugloar.models;


public class Message {

    private String adId;
    private String message;
    private long reward;
    private long expiresIn;
    private Integer encrypted;
    private String probability;

    public Message() {
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getReward() {
        return reward;
    }

    public void setReward(long reward) {
        this.reward = reward;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Integer encrypted) {
        this.encrypted = encrypted;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

}
