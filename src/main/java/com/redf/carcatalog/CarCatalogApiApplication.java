package com.redf.carcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarCatalogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarCatalogApiApplication.class, args);
	}

}
