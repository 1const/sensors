package com.example.springrest.repository;

import com.example.springrest.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Integer countByRaining(Boolean raining);
}
