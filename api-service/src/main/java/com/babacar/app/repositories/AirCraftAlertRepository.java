package com.babacar.app.repositories;

import com.babacar.app.entities.AirCraftAlert;
import com.babacar.app.entities.AirCraftProsseed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AirCraftAlertRepository extends MongoRepository<AirCraftAlert,String> {
    List<AirCraftAlert> findByOriginCountry(String country);
    Optional<AirCraftAlert> findByUuid(String uuid);

}
