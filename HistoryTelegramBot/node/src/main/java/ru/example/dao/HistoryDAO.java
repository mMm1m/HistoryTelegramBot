package ru.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.entity.HistoryData;

import java.util.List;

@Repository
public interface HistoryDAO extends JpaRepository<HistoryData, Long> {
    List<HistoryData> findByEpoch(String epoch);
    boolean existsByEpoch(String epoch);
    boolean existsByEvent(String event);
    List<HistoryData> findByEvent(String event);
    boolean existsByReference(String reference);

}
