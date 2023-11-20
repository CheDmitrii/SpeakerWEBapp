package ru.dmitrii.speakerWEBapp.models;

import java.util.List;

public class Album {
    private int id;
    private String name;
    private List<Song> songs;
    private List<Artist> artists;
    private String label;

    public Album(){}
    public Album(int id, String name, List<Song> songs, List<Artist> artists, String label) {
        this.id = id;
        this.name = name;
        this.songs = songs;
        this.artists = artists;
        this.label = label;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Artist> getArtists() {
        return artists;
    }
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
}