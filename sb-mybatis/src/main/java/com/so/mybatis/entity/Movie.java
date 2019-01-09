package com.so.mybatis.entity;

import com.so.mybatis.commom.Enum.MovieType;

public class Movie {
    private long id = -1;
    private String movieName;
    private int boxoffice = -1;
    private MovieType type = MovieType.NA;

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

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }
}
