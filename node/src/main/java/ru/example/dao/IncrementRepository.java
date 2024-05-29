package ru.example.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.Increment;

@Repository
public interface IncrementRepository extends CrudRepository<Increment, String> {
}
