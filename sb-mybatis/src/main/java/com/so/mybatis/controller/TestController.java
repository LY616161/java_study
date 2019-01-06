package com.so.mybatis.controller;

import com.so.mybatis.entity.Movie;
import com.so.mybatis.mapper.MovieMapper;
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
