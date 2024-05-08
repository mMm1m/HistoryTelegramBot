package ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.example.service.enums.Epochs;

@SpringBootApplication
public class NodeApplication {
    //public static String state;
    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class);
    }
}
