package com.so.movie.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.so.movie.entity.Dreamer;
import com.so.movie.entity.Movie;
import com.so.movie.fegin.DreamerFeignClient;
import com.so.movie.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private DreamerFeignClient dreamerFeignClient;
    @GetMapping("/movie/{id}")
    public Movie quertMovieById(@PathVariable("id")int id){
        return movieMapper.queryMovieById(id);
    }

    @GetMapping("/feign/{id}")
    public Dreamer queryDreamerByFeign(@PathVariable("id")int id){
        return dreamerFeignClient.findDreamerById(id);
    }
}
