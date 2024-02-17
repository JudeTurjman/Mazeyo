package com.jude.mazeyo;

public class User {


    private String username;
    private String comment;
    private String photo;
    private int easy;
    private int medium;
    private int hard;
    private int coin;

    public User(String username) {
        this.username = username;
        comment = "hi, I am a Mazeyo Player";
        easy = 0;
        medium = 0;
        hard = 0;
        coin = 0;
    }

    public User() {
        this.username = username;
        this.comment = comment;
        this.photo = photo;
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.coin = coin;
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
