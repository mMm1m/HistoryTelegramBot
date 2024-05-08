package ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication {
    public static volatile String state;
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class);
    }
}
