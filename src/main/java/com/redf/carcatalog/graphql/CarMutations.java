package com.redf.carcatalog.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.redf.carcatalog.model.CarEntity;
import com.redf.carcatalog.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMutations implements GraphQLMutationResolver {

    private Logger logger = LoggerFactory.getLogger(CarMutations.class);
    private CarService carService;


    @Autowired
    CarMutations(CarService carService) {
        setCarService(carService);
    }


    CarMutations() {}


    public CarEntity newCar(CarEntity car) {
        logger.info("GraphQL add Car={}", car);
        car =  getCarService().addCar(car);
        if(car == null) {
            logger.info("GraphQL ADD Car BAD REQUEST");
            return null;
        }
        else {
            logger.info("GraphQL ADD Car CREATED car={}", car);
            return car;
        }
    }


    public boolean deleteCar(Long id) {
        boolean deleted = getCarService().deleteCar(id);
        if(!deleted) {
            logger.info("GraphQL DELETE Car id={} NOT FOUND", id);
            return false;
        }
        else {
            logger.info("GraphQL DELETE Car id={} OK", id);
            return true;
        }
    }


    private CarService getCarService() {
        return carService;
    }


    private void setCarService(CarService carService) {
        this.carService = carService;
    }
}
