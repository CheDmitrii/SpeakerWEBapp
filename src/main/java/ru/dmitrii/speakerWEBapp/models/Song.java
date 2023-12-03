package ru.dmitrii.speakerWEBapp.models;

import java.util.List;

public class Song {
    private int id;
    private int idAlbum;
    private String name;
    private List<Artist> Artists;
    private List<Artist> subArtists;
    private String text;
    private String path;
    private int limValue;
    private boolean isAdd;

    public Song() {}

    public Song(int id, int idAlbum, String name,
                List<Artist> artists, List<Artist> subArtists,
                String text, String path, int limValue, boolean isAdd) {
        this.id = id;
        this.idAlbum = idAlbum;
        this.name = name;
        Artists = artists;
        this.subArtists = subArtists;
        this.text = text;
        this.path = path;
        this.limValue = limValue;
        this.isAdd = isAdd;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlbum() {
        return idAlbum;
    }
    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtists() {
        return Artists;
    }
    public void setArtists(List<Artist> idArtists) {
        this.Artists = idArtists;
    }

    public List<Artist> getSubArtists() {
        return subArtists;
    }
    public void setSubArtists(List<Artist> subArtists) {
        this.subArtists = subArtists;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public int getLimValue() {
        return limValue;
    }
    public void setLimValue(int limValue) {
        this.limValue = limValue;
    }

    public boolean isAdd() {
        return isAdd;
    }
    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean hasFeats() {
        return subArtists != null && !subArtists.isEmpty();
    }
}
