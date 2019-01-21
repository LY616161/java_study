package com.so.dreamer.controller;

import com.so.dreamer.entity.Dreamer;
import com.so.dreamer.mapper.DreamerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DreamerController {
    @Autowired
    private DreamerMapper dreamerMapper;

    @GetMapping("/dreamer/{id}")
    public Dreamer queryDreamerById(@PathVariable("id") int id){
        return dreamerMapper.queryDreamerById(id);
    }
}
