package com.so.sbmytatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.so.sbmytatis.mapper")
public class SbMytatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbMytatisApplication.class, args);
	}

}

