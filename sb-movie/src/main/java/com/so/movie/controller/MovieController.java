package com.so.movie.controller;

import com.so.movie.entity.Movie;
import com.so.movie.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    @Autowired
    private MovieMapper movieMapper;
    @GetMapping("/movie/{id}")
    public Movie quertMovieById(@PathVariable("id")int id){
        return movieMapper.queryMovieById(id);
    }
}
