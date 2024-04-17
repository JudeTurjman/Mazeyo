package com.jude.mazeyo;

import java.util.Calendar;
import java.util.Date;

public class User {


    private String username;
    private String comment;
    private String photo;
    private int easy;
    private int medium;
    private int hard;
    private int coin;
    private int dailyCount;
    private boolean didDaily;
    private Date datePlay;

    public User() {
    }

    public User(String username) {
        this.username = username;
        comment = "hi, I am a Mazeyo Player";
        easy = 0;
        medium = 0;
        hard = 0;
        coin = 0;
        dailyCount = 0;
        didDaily = false;
        datePlay = Calendar.getInstance().getTime();

    }
    public User(String username, String comment, String photo, int easy, int medium, int hard, int coin, int dailyCount, boolean didDaily, Date datePlay) {
        this.username = username;
        this.comment = comment;
        this.photo = photo;
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.coin = coin;
        this.dailyCount = dailyCount;
        this.didDaily = didDaily;
        this.datePlay = datePlay;
    }

    public Date getDatePlay() {
        return datePlay;
    }

    public void setDatePlay(Date datePlay) {
        this.datePlay = datePlay;
    }

    public boolean getDidDaily() {
        return didDaily;
    }

    public void setDidDaily(boolean didDaily) {
        this.didDaily = didDaily;
    }

    public int getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(int dalyCount) {
        this.dailyCount = dalyCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getEasy() {
        return easy;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getHard() {
        return hard;
    }

    public void setHard(int hard) {
        this.hard = hard;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", photo='" + photo + '\'' +
                ", easy=" + easy +
                ", medium=" + medium +
                ", hard=" + hard +
                ", coin=" + coin +
                '}';
    }
}
