package com.cse40333.pdrumm.lab2_pdrumm;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

class Team implements Serializable {
    private String teamName;
    private String teamNickname;
    private String teamLogo;
    private int teamWins;
    private int teamLosses;
    private String teamStadium;
    private Game game;

    public Team(String teamName, String teamNickname, String teamLogo, int teamWins, int teamLosses, String teamStadium) {
        this.teamName = teamName;
        this.teamNickname = teamNickname;
        this.teamLogo = teamLogo;
        this.teamWins = teamWins;
        this.teamLosses = teamLosses;
        this.teamStadium = teamStadium;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamNickname() {
        return teamNickname;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public int getTeamWins() {
        return teamWins;
    }

    public int getTeamLosses() {
        return teamLosses;
    }

    public String getTeamRecord() {
        return "("+getTeamWins()+"-"+getTeamLosses()+")";
    }

    public String getTeamStadium() {
        return teamStadium;
    }

    public Game getGame() {
        return game;
    }

    public String getGameString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.teamName + ", ");
        sb.append(this.game.getHomeTeam().teamStadium + ", ");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        sb.append(df.format(this.game.getGameDate()));
        sb.append(")");
        return sb.toString();
    }
}
