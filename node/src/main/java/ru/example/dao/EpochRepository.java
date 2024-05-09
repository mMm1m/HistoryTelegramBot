package ru.example.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.Epoch;

@Repository
public interface EpochRepository extends CrudRepository<Epoch, String> {}

