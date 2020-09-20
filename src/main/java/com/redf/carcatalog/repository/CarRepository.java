package com.redf.carcatalog.repository;


import com.redf.carcatalog.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Query(value = "select * from Car where " +
            "(:brand is null or lower(brand) = lower(cast(:brand as varchar))) " +
            "and (:model is null or lower(model) =  lower(cast(:model as varchar))) " +
            "and (:color is null or lower(color) =  lower(cast(:color as varchar))) " +
            "and (:registrationNumber is null or upper(registration_number) = upper(cast(:registrationNumber as varchar))) " +
            "and (:yearTo is null or year_of_issue <= cast((cast(:yearTo as varchar)) as integer)) " +
            "and (:yearFrom is null or year_of_issue >= cast((cast(:yearFrom as varchar)) as integer))", nativeQuery = true)
    List<CarEntity> findAllCarsFiltered(@Param("brand") String brand, @Param("model") String model, @Param("color") String color,
                                        @Param("registrationNumber") String registrationNumber, @Param("yearTo") Integer yearTo, @Param("yearFrom") Integer yearFrom);


    Optional<CarEntity> findByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    @Query(value = "select created_at from car order by created_at asc limit 1", nativeQuery = true)
    Timestamp getOldestRecord();

    @Query(value = "select created_at from car order by created_at desc limit 1", nativeQuery = true)
    Timestamp getLatestRecord();
}
