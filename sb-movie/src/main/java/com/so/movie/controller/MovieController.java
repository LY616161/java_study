package com.so.movie.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.so.movie.common.utils.DateUtils;
import com.so.movie.entity.Dreamer;
import com.so.movie.entity.Movie;
import com.so.movie.fegin.DreamerFeignClient;
import com.so.movie.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;

@RestController
public class MovieController {
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private DreamerFeignClient dreamerFeignClient;

    @GetMapping("/movie/{id}")
    public Movie queryMovieById(@PathVariable("id")int id){
        return movieMapper.queryMovieById(id);
    }

    @GetMapping("/feign/{id}")
    public Dreamer queryDreamerByFeign(@PathVariable("id")int id){
        return dreamerFeignClient.findDreamerById(id);
    }
    @PostMapping("movie/insert")
    public String insertMovie(@RequestParam(value = "name")String name,
                              @RequestParam(value = "directorId")int directorId,
                              @RequestParam(value = "releaseDate")String releaseDate
    ){
        Movie movie = new Movie();
        movie.setName(name);
        movie.setDirectorId(directorId);
        movie.setReleaseDate(Date.valueOf(releaseDate));
        movieMapper.insertMovie(movie);
        return "success";
    }

    @PostMapping("movie/update")
    public String updateMovie(@RequestParam(value = "name",required = false)String name,
                              @RequestParam(value = "directorId",required = false)Integer directorId,
                              @RequestParam(value = "releaseDate",required = false)String releaseDate,
                              @RequestParam(value = "id")Integer id) throws ParseException {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setDirectorId(directorId);
        movie.setReleaseDate(Date.valueOf(releaseDate));
        movie.setId(id);
        movieMapper.updateMovie(movie);
        return "success" + DateUtils.getdateTime();
    }

}
