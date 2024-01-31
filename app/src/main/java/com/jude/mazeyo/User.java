package com.jude.mazeyo;

public class User {

    private String firstname;
    private String lastname;
    private String username;
    private String photo;

    public User() {
    }

    public User(String firstname, String lastname, String username, String photo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.photo = photo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
