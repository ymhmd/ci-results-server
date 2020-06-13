package com.jenkins.CITracker.repositories;

import com.jenkins.CITracker.entities.CIResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CIResultRepository extends JpaRepository<CIResult, Long> {
    List<CIResult> findByType(String type);
    List<CIResult> findAllByOrderByDateDesc();
    List<CIResult> findAll();
}
