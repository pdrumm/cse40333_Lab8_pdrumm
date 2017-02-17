package com.cse40333.pdrumm.lab2_pdrumm;


import java.io.Serializable;
import java.util.Date;

class Game implements Serializable {
    private Date gameDate;
    private Team awayTeam;
    private Team homeTeam;
    private int homeFirstHalfScore;
    private int homeSecondHalfScore;
    private int awayFirstHalfScore;
    private int awaySecondHalfScore;
    private int homeFinalScore;
    private int awayFinalScore;

    public Game(Date gameDate, Team awayTeam, Team homeTeam) {
        this.gameDate = gameDate;
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
    }

    public void setHomeScore(int firstHalf, int secondHalf) {
        this.homeFirstHalfScore = firstHalf;
        this.homeSecondHalfScore = secondHalf;
        this.homeFinalScore = firstHalf + secondHalf;
    }

    public void setAwayScore(int firstHalf, int secondHalf) {
        this.awayFirstHalfScore = firstHalf;
        this.awaySecondHalfScore = secondHalf;
        this.awayFinalScore = firstHalf + secondHalf;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public int getHomeFirstHalfScore() {
        return homeFirstHalfScore;
    }

    public int getHomeSecondHalfScore() {
        return homeSecondHalfScore;
    }

    public int getAwayFirstHalfScore() {
        return awayFirstHalfScore;
    }

    public int getAwaySecondHalfScore() {
        return awaySecondHalfScore;
    }

    public int getHomeFinalScore() {
        return homeFinalScore;
    }

    public int getAwayFinalScore() {
        return awayFinalScore;
    }

    public String getFinalScore() {
        return awayFinalScore+"-"+homeFinalScore;
    }
}
