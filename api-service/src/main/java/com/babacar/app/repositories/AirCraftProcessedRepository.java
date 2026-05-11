package com.babacar.app.repositories;

import com.babacar.app.entities.AirCraftProsseed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AirCraftProcessedRepository extends MongoRepository<AirCraftProsseed,String> {
    List<AirCraftProsseed> findByOriginCountry(String country);
    Optional<AirCraftProsseed> findByUuid(String uuid);

}
