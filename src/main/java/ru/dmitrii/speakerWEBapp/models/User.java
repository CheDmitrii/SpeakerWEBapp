package ru.dmitrii.speakerWEBapp.models;

public class User {
    private int id;
    private String userName;
    private String firstName;
    private String secondName;
    private int limitValue;

    public User(){}
    public User(int id, String userName, String firstName, String secondName, int limitValue) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.limitValue = limitValue;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getLimitValue() {
        return limitValue;
    }
    public void setLimitValue(int limitValue) {
        this.limitValue = limitValue;
    }
}
