package com.example.dragonsofmugloar.models;


public class Game {

    private String gameId;
    private long lives;
    private long gold;
    private long level;
    private long score;

    public Game() {
    }


    public String getGameId() {
        return gameId;
    }

    public long getLives() {
        return lives;
    }

    public void setLives(long lives) {
        this.lives = lives;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
