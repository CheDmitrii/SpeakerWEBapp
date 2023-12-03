package ru.dmitrii.speakerWEBapp.models;

public class Subscribe {
    private int idSubscribe;
    private double cost;
    private String name;

    public Subscribe() {
    }

    public Subscribe(int idSubscribe, double cost, String name) {
        this.idSubscribe = idSubscribe;
        this.cost = cost;
        this.name = name;
    }

    public int getIdSubscribe() {
        return idSubscribe;
    }
    public void setIdSubscribe(int idSubscribe) {
        this.idSubscribe = idSubscribe;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
