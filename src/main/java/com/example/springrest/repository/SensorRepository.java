package com.example.springrest.repository;

import com.example.springrest.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}
