package com.fucct.reactivepractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@SpringBootApplication
public class ReactivePracticeApplication implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ReactivePracticeApplication.class, args);
    }
}
