package com.example.springrest.util;

import com.example.springrest.dto.MeasurementDTO;
import com.example.springrest.dto.SensorDTO;
import com.example.springrest.entity.Measurement;
import com.example.springrest.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        SensorDTO sensorDTO = measurementDTO.getSensor();
        if (Objects.isNull(sensorDTO) || Objects.isNull(sensorService.findByName(sensorDTO.getName()))) {
            errors.rejectValue("sensor", "", "sensor doesn't exist");
        }
    }
}
