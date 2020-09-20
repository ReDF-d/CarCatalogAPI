package com.redf.carcatalog;

import com.redf.carcatalog.model.CarEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpHeaders;


import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CarCatalogApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarCatalogApiApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testGetAllCars() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/cars",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
	}

	@Test
	void testGetCarById() {
		CarEntity car = restTemplate.getForObject(getRootUrl() + "/cars/1", CarEntity.class);
		System.out.println(car.getRegistrationNumber());
		assertNotNull(car);
	}

	@Test
	void testCreateCar() {
		CarEntity car = new CarEntity();
		car.setId(1L);
		car.setBrand("Renault");
		car.setModel("Logan");
		car.setRegistrationNumber("A123AA196");
		car.setColor("white");
		car.setYearOfIssue(2009);
		car.setCreatedAt(Timestamp.from(Instant.now()));
		ResponseEntity<CarEntity> postResponse = restTemplate.postForEntity(getRootUrl() + "/cars", car, CarEntity.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}


	@Test
	void testDeleteCar() {
		int id = 2;
		CarEntity car = restTemplate.getForObject(getRootUrl() + "/cars/" + id, CarEntity.class);
		assertNotNull(car);
		restTemplate.delete(getRootUrl() + "/cars/" + id);
		try {
			restTemplate.getForObject(getRootUrl() + "/cars/" + id, CarEntity.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
