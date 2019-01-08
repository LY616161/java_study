package com.elasticsearch.sbelasticsearch.controller;

import com.elasticsearch.sbelasticsearch.entity.Movie;
import com.elasticsearch.sbelasticsearch.mapper.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("/insert")
    public String add(Movie movie){
        movieRepository.save(movie);
        return "success";
    }

    @GetMapping("/query/{id}")
    public Movie query(@PathVariable("id") long id){
        return movieRepository.queryMovieById(id);
    }
}
