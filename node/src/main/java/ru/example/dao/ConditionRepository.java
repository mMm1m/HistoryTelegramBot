package ru.example.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.Condition;

@Repository
public interface ConditionRepository extends CrudRepository<Condition, String> {
}
