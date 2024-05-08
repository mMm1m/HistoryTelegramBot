package ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DispatcherApplication {
    //public static String state = NodeApplication.state;
    public static void main(String[] args) {
        SpringApplication.run(DispatcherApplication.class);
    }
}
