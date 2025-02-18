package com.lcmartins.infrastructure;

import com.lcmartins.infrastructure.configuration.ApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(ApiConfig.class, args);
        System.out.println("Startou!");
    }
}