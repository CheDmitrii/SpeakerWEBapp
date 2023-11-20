package ru.dmitrii.speakerWEBapp.models;

public class Artist {
    private int id;
    private String pseudonym;
    private int age;
    private String bithday;
    private String country;

    public Artist(){}
    public Artist(int id, String pseudonym, int age, String bithday, String country) {
        this.id = id;
        this.pseudonym = pseudonym;
        this.age = age;
        this.bithday = bithday;
        this.country = country;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPseudonym() {
        return pseudonym;
    }
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getBithday() {
        return bithday;
    }
    public void setBithday(String bithday) {
        this.bithday = bithday;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
