package com.so.dreamer.controller;

import com.so.dreamer.entity.Dreamer;
import com.so.dreamer.mapper.DreamerMapper;
import com.so.dreamer.utils.ServiceInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DreamerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DreamerController.class);
    @Autowired
    private DreamerMapper dreamerMapper;

    @GetMapping("/dreamer/{id}")
    public Dreamer queryDreamerById(@PathVariable("id") int id){
        LOGGER.info("queryDreamerById invoke");
        return dreamerMapper.queryDreamerById(id);
    }
}
