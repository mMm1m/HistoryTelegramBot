package ru.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RedisHash("Epoch")
public class Epoch implements Serializable {

    private String id;
    private String epoch;
    //private int grade;
}