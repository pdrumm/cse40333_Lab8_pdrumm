package com.cse40333.pdrumm.lab2_pdrumm;


import android.content.ContentValues;

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

    public ContentValues toContentValue(DBHelper dbHelper) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.C_GAME_DATE, gameDate.getTime());
        contentValues.put(dbHelper.C_GAME_AWAY_TEAM_ID, awayTeam.getId());
        contentValues.put(dbHelper.C_GAME_HOME_TEAM_ID, homeTeam.getId());
        contentValues.put(dbHelper.C_GAME_HOME_FIRST_HALF_SCORE, homeFirstHalfScore);
        contentValues.put(dbHelper.C_GAME_HOME_SECOND_HALF_SCORE, homeSecondHalfScore);
        contentValues.put(dbHelper.C_GAME_HOME_FINAL_SCORE, homeFinalScore);
        contentValues.put(dbHelper.C_GAME_AWAY_FIRST_HALF_SCORE, awayFirstHalfScore);
        contentValues.put(dbHelper.C_GAME_AWAY_SECOND_HALF_SCORE, awaySecondHalfScore);
        contentValues.put(dbHelper.C_GAME_AWAYS_FINAL_SCORE, awayFinalScore);
        return contentValues;
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
