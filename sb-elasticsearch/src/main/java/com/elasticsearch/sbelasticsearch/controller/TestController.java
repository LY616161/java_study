package com.elasticsearch.sbelasticsearch.controller;

import com.elasticsearch.sbelasticsearch.entity.Movie;
import com.elasticsearch.sbelasticsearch.mapper.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/searchByName")
    public Page<Movie> searchByName(@RequestParam("name") String name,
                             @RequestParam("pageIndex") int pageIndex,
                             @RequestParam("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return movieRepository.findByMovieNameLike(name,pageable);
    }

    @GetMapping("/searchByNameOrDirector")
    public Page<Movie> searchByNameOrDirector(@RequestParam("name") String name,
                             @RequestParam("pageIndex") int pageIndex,
                             @RequestParam("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return movieRepository.findByMovieNameLikeOrDirectorLike(name,name,pageable);
    }
}
