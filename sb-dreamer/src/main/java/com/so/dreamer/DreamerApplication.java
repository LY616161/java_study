package com.so.dreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamerApplication.class, args);
	}

}

