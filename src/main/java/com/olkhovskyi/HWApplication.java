package com.olkhovskyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class HWApplication {
    public static void main(String[] args) {
        SpringApplication.run(HWApplication.class, args);
    }
}
