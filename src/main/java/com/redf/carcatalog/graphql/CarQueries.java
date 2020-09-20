package com.redf.carcatalog.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.redf.carcatalog.model.CarEntity;
import com.redf.carcatalog.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarQueries implements GraphQLQueryResolver {

    private Logger logger = LoggerFactory.getLogger(CarQueries.class);

   
    private CarService carService;

    CarQueries() {}

    @Autowired
    CarQueries(CarService carService) {
        setCarService(carService);
    }


    public List<CarEntity> getCars() {
        logger.info("GraphQL getAllCars");
        return getCarService().getAllCars();
    }


    public List<CarEntity> getCarsByParams(String brand, String model, String color, String registrationNumber, String yearTo, String yearFrom) {
        logger.info("GraphQL getAllCarsByParams");
        return getCarService().getAllCars(brand, model, color, registrationNumber, yearTo, yearFrom);
    }



    public CarEntity getCar(Long id) {
        logger.info("GraphQL getCarById id={}", id);
        return getCarService().getCarById(id);
    }


    private CarService getCarService() {
        return carService;
    }

    private void setCarService(CarService carService) {
        this.carService = carService;
    }
}
