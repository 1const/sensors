package com.example.springrest.controller;

import com.example.springrest.dto.SensorDTO;
import com.example.springrest.entity.Measurement;
import com.example.springrest.entity.Sensor;
import com.example.springrest.exception.SensorCreateException;
import com.example.springrest.service.SensorService;
import com.example.springrest.util.SensorErrorResponse;
import com.example.springrest.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService,
                            SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")

    public ResponseEntity<HttpStatus> save(@RequestBody @Valid SensorDTO sensorDTO,
                                           BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder msg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> msg.append(fieldError.getDefaultMessage()));
            throw new SensorCreateException(msg.toString());
        }
        sensorService.save(modelMapper.map(sensorDTO, Sensor.class));
        return new ResponseEntity<>(HttpStatus.CREATED); //201
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> exceptionHandler(SensorCreateException sensorExistException) {
        return new ResponseEntity<>(new SensorErrorResponse(sensorExistException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
