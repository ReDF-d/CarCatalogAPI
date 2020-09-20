package com.redf.carcatalog.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "car")
public class CarEntity {

    private Long id;
    private String model;
    private String color;
    private int yearOfIssue;
    private String registrationNumber;
    private String brand;
    private Timestamp createdAt;


    public CarEntity() {}


    public CarEntity(Long id, String model, String color, int yearOfIssue, String registrationNumber, String brand, Timestamp createdAt) {
        setId(id);
        setModel(model);
        setColor(color);
        setYearOfIssue(yearOfIssue);
        setRegistrationNumber(registrationNumber);
        setBrand(brand);
        setCreatedAt(createdAt);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "model", nullable = false)
    @JsonProperty("model")
    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }


    @Column(name = "color", nullable = false)
    @JsonProperty("color")
    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    @Column(name = "year_of_issue", nullable = false)
    @JsonProperty("yearOfIssue")
    public int getYearOfIssue() {
        return yearOfIssue;
    }


    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }


    @Column(name = "registration_number", nullable = false, unique = true)
    @JsonProperty("registrationNumber")
    public String getRegistrationNumber() {
        return registrationNumber;
    }


    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }


    @Column(name = "brand", nullable = false)
    @JsonProperty("brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    public Timestamp getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return getId() + " " + getBrand() + " " + getModel() + " " +
                getColor() + " " + getRegistrationNumber() + " "
                + getYearOfIssue() + " " + getCreatedAt();
    }
}
