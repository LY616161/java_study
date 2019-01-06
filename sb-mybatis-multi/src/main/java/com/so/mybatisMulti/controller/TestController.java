package com.so.mybatisMulti.controller;

import com.so.mybatisMulti.entity.primary.Movie;
import com.so.mybatisMulti.entity.secondary.FilmMaker;
import com.so.mybatisMulti.mapper.primary.MovieMapper;
import com.so.mybatisMulti.mapper.secondary.FilmMakerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private FilmMakerMapper filmMakerMapper;

    @GetMapping("/movie/{id}")
    public Movie selectMovieById(@PathVariable("id") long id){
        return movieMapper.selectMovieById(id);
    }

    @GetMapping("/maker/{id}")
    public FilmMaker selectMakerById(@PathVariable("id") long id){
        LOGGER.info("selectMakerById invoke");
        return filmMakerMapper.selectMakerById(id);
    }

}
