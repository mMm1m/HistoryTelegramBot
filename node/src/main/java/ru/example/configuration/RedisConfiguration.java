package ru.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.example.dao.ConditionRepository;
import ru.example.dao.EpochRepository;
import ru.example.dao.IncrementRepository;
import ru.example.entity.Condition;
import ru.example.entity.Epoch;
import ru.example.entity.Increment;

import java.util.*;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfiguration {
    @Autowired
    EpochRepository repository;
    @Autowired
    ConditionRepository  conditionRepository;
    @Autowired
    IncrementRepository incrementRepository;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @PostConstruct
    public void redisInit()
    {
        Epoch start_epoch = new Epoch("state", "");
        Condition condition = new Condition("condition", "");
        Increment increment = new Increment("inc", 0);
        if(repository.count() == 0)
        {
            repository.save(start_epoch);
        }
        if(conditionRepository.count() == 0)
        {
            conditionRepository.save(condition);
        }
        if(incrementRepository.count() == 0)
        {
            incrementRepository.save(increment);
        }
    }
}
