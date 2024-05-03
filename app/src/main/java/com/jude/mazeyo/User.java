package com.jude.mazeyo;

import java.util.ArrayList;
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
    private ArrayList<String> ownedSkins;
    private String inUse;
    private ArrayList<String> ownedMaps;
    private String inMap;

    public User() {
    }

    public User(String username) {
        this.username = username;
        comment = "hi, I am a Mazeyo Player";
        easy = 0;
        medium = 0;
        hard = 0;
        coin = 150;
        dailyCount = 0;
        didDaily = false;
        datePlay = Calendar.getInstance().getTime();
        photo = null;
        ownedSkins = new ArrayList<String>();
        ownedSkins.add("Red");
        inUse = "Red";
        ownedMaps = new ArrayList<String>();
        ownedMaps.add("White");
        inMap = null;
    }

    public ArrayList<String> getOwnedMaps() {
        return ownedMaps;
    }

    public void setOwnedMaps(ArrayList<String> ownedMaps) {
        this.ownedMaps = ownedMaps;
    }

    public String getInMap() {
        return inMap;
    }

    public void setInMap(String inMap) {
        this.inMap = inMap;
    }

    public ArrayList<String> getOwnedSkins() {
        return ownedSkins;
    }

    public void setOwnedSkins(ArrayList<String> ownedSkins) {
        this.ownedSkins = ownedSkins;
    }

    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
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
                ", dailyCount=" + dailyCount +
                ", didDaily=" + didDaily +
                ", datePlay=" + datePlay +
                ", ownedSkins=" + ownedSkins +
                ", inUse='" + inUse + '\'' +
                ", ownedMaps=" + ownedMaps +
                ", inMap='" + inMap + '\'' +
                '}';
    }
}
