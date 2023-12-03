package ru.dmitrii.speakerWEBapp.models;

import lombok.Data;

@Data
public class Limitation {
    private int id;
    private int value;
    private String name;

    public Limitation() {}

    public Limitation(int id, int value, String name) {
        this.id = id;
        this.value = value;
        this.name = name;
    }
}
