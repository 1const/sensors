package com.example.springrest.controller;

import com.example.springrest.dto.MeasurementDTO;
import com.example.springrest.dto.MeasurementResponse;
import com.example.springrest.entity.Measurement;
import com.example.springrest.service.MeasurementService;
import com.example.springrest.exception.MeasurementCreateException;
import com.example.springrest.util.MeasurementErrorResponse;
import com.example.springrest.util.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper,
            MeasurementValidator measurementValidator) {

        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(
            @RequestBody MeasurementDTO measurementDTO, BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder stringBuilder = new StringBuilder();
            fieldErrors.forEach(fieldError -> stringBuilder.append(fieldError.getDefaultMessage()));
            throw new MeasurementCreateException(stringBuilder.toString());
        }
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping
    public MeasurementResponse index(){
        List<MeasurementDTO> response = measurementService.findAll()
                .stream()
                .map(measurement -> modelMapper.map(measurement, MeasurementDTO.class))
                .toList();
        return new MeasurementResponse(response);
    }

    @GetMapping("/rainyDaysCount")
    public Integer getRainyDaysCount(){
        return measurementService.getRainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(
            MeasurementCreateException measurementCreateException) {

        return new ResponseEntity<>(new MeasurementErrorResponse(measurementCreateException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
