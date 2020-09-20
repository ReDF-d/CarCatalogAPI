package com.redf.carcatalog.service;

import com.redf.carcatalog.model.CarEntity;
import com.redf.carcatalog.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CarService {

    private Logger logger = LoggerFactory.getLogger(CarService.class);
    private CarRepository carRepository;

    CarService() {
    }


    @Autowired
    CarService(CarRepository carRepository) {
        setCarRepository(carRepository);
    }


    @Cacheable(value = "cars")
    public CarEntity getCarById(Long id) {
        Optional car = getCarRepository().findById(id);
        if (car.isPresent()) {
            logger.info("GET car " + car.get().toString() + " OK");
            return (CarEntity) car.get();
        } else {
            logger.info("GET car " + id + " NOT FOUND");
            return null;
        }
    }


    @Cacheable(value = "cars")
    public List<CarEntity> getAllCars(String brand, String model, String color, String registrationNumber, String yearTo, String yearFrom) {
        List<CarEntity> cars;
        try {
            cars = getCarRepository().findAllCarsFiltered(brand, model, color, registrationNumber, parseNullableParam(yearTo), parseNullableParam(yearFrom));
        } catch (NumberFormatException nfe) {
            logger.info("GET allCars BAD REQUEST");
            return null;
        }
        logger.info("GET allCars OK");
        return cars;
    }


    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @CacheEvict(value = "cars", allEntries = true)
    public boolean addCar(CarEntity car) {
        Optional existing = getCarRepository().findByRegistrationNumber(car.getRegistrationNumber());
        if (existing.isPresent()) {
            logger.info("POST car " + existing.toString() + " CONFLICT");
            return false;
        } else {
            car = getCarRepository().save(car);
            logger.info("POST car " + car.toString() + " CREATED");
            return true;
        }
    }


    @CacheEvict(value = "cars", allEntries = true)
    public boolean deleteCar(Long id) {
        if (getCarRepository().findById(id).isEmpty()) {
            logger.info("DELETE car " + id + " NOT FOUND");
            return false;
        } else {
            getCarRepository().delete(getCarRepository().findById(id).get());
            logger.info("DELETE car " + id + " NO CONTENT");
            return true;
        }
    }


    public Map<String, String> getStatistics() {
        HashMap<String, String> statistics = new HashMap<>();
        statistics.put("Record count", String.valueOf(getCarRepository().findAll().size()));
        statistics.put("Oldest record", getCarRepository().getOldestRecord().toString());
        statistics.put("Latest record", getCarRepository().getLatestRecord().toString());
        logger.info("GET stats OK");
        return statistics;
    }


    private Integer parseNullableParam(String param) throws NumberFormatException {
        if (param != null)
            return Integer.parseInt(param);
        return null;
    }


    private CarRepository getCarRepository() {
        return carRepository;
    }


    private void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
}
