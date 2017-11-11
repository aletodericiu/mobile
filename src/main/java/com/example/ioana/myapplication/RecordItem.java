package com.example.ioana.myapplication;

/**
 * Created by Ioana on 11/11/2017.
 */

public class RecordItem {

    String name;
    String band;
    String genre;

    public RecordItem(String name, String band, String genre) {
        this.name = name;
        this.band = band;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
