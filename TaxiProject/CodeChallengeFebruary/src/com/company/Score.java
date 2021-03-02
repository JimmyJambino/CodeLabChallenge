package com.company;

import java.util.Comparator;

public class Score {

    // ##############################################
    // ########## - Variables - #####################
    // ##############################################
    private String playerName;
    private int score; // moves made

    public Score(String playerName, int score) {
        setPlayerName(playerName);
        setScore(score);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {
        return score;
    }

    public int compareTo(int score) {
        if(score > this.score) {
            return score;
        } else {
            return this.score;
        }
    }

}
