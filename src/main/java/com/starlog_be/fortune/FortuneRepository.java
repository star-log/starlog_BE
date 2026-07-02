package com.starlog_be.fortune;

import com.starlog_be.fortune.domain.Fortune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FortuneRepository extends JpaRepository<Fortune, Long> {
    public List<Fortune> findByDate(LocalDate now);

    public Fortune findByid(Long id);
}
