package com.example.springrest.service;

import com.example.springrest.entity.Measurement;
import com.example.springrest.entity.Sensor;
import com.example.springrest.repository.MeasurementRepository;
import com.example.springrest.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    @Autowired
    public MeasurementService(
            MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        initializeTime(measurement);
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName());
        measurement.setSensor(sensor);
        measurementRepository.save(measurement);
    }

    public Integer getRainyDaysCount() {
        return measurementRepository.countByRaining(true);
    }

    private void initializeTime(Measurement measurement) {
        measurement.setMeasurementTime(LocalDateTime.now());
    }
}
