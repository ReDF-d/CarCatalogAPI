package com.redf.carcatalog.controller;


import com.redf.carcatalog.service.CarService;
import com.redf.carcatalog.model.CarEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class CarController {

    private Logger logger = LoggerFactory.getLogger(CarService.class);
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        setCarService(carService);
    }


    @GetMapping("/cars/{id}")
    public ResponseEntity getCar(@PathVariable("id") String id) {
        try {
            CarEntity car = getCarService().getCarById(Long.parseLong(id));
            if (car != null)
                return ResponseEntity.ok(car);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND.getReasonPhrase());
        } catch (NumberFormatException nfe) {
            logger.info("GET car " + id + " BAD REQUEST");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }


    @GetMapping("/cars")
    public ResponseEntity getAllCars(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "registrationNumber", required = false) String registrationNumber,
            @RequestParam(name = "yearTo", required = false) String yearTo,
            @RequestParam(name = "yearFrom", required = false) String yearFrom) {
        List<CarEntity> cars = getCarService().getAllCars(brand, model, color, registrationNumber, yearTo, yearFrom);
        if (cars == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(cars);
    }


    @PostMapping("/cars/add")
    public ResponseEntity addCar(@RequestBody CarEntity car) {
        if (car == null || car.getId() != null || car.getCreatedAt() != null) {
            logger.info("POST car BAD REQUEST");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        car = getCarService().addCar(car);
        if (car == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(HttpStatus.CONFLICT.getReasonPhrase());
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED.getReasonPhrase());
    }


    @DeleteMapping("/cars/delete")
    public ResponseEntity deleteCar(@RequestParam(name = "id") String id) {
        long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            logger.info("DELETE car " + id + " BAD REQUEST");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        boolean deleted = getCarService().deleteCar(parsedId);
        if (!deleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(HttpStatus.NO_CONTENT.getReasonPhrase());
    }


    @GetMapping("/stats")
    public ResponseEntity getStatistics() {
        return ResponseEntity.ok(getCarService().getStatistics());
    }


    private CarService getCarService() {
        return carService;
    }


    private void setCarService(CarService carService) {
        this.carService = carService;
    }
}
