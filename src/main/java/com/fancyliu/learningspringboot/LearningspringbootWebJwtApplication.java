package com.fancyliu.learningspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.fancyliu.learningspringboot.model")
public class LearningspringbootWebJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningspringbootWebJwtApplication.class, args);
    }

}
