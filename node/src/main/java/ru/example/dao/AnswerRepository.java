package ru.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.Answers;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {
    boolean existsByTASK(String task);
   // @Override
   // public Answers save(Answers answers);
}
