package com.hoostec.hfz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HafuzhuApplication {

    public static void main(String[] args) {
        SpringApplication.run(HafuzhuApplication.class, args);
    }

}
