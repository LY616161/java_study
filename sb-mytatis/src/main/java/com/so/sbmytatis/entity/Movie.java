package com.so.sbmytatis.entity;

public class Movie {
    private long id = -1;
    private String movieName;
    private int boxoffice = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(int boxoffice) {
        this.boxoffice = boxoffice;
    }
}