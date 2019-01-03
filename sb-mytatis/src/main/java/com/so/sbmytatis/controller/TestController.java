package com.so.sbmytatis.controller;

import com.so.sbmytatis.entity.Movie;
import com.so.sbmytatis.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MovieMapper movieMapper;

    @GetMapping("/movie/{id}")
    public Movie selectMovieById(@PathVariable("id") long id){
        return movieMapper.selectMovieById(id);
    }

    @PostMapping("/movie/insert")
    public void insertMovie(Movie movie){
        movieMapper.insertMovie(movie);
    }

}
