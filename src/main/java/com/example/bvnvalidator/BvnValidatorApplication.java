package com.example.bvnvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BvnValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BvnValidatorApplication.class, args);
    }

}
