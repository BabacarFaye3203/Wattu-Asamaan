package com.babacar.app.mappers;

import com.babacar.app.dto.responses.AircraftResponse;
import com.babacar.app.entities.AirCraftAlert;
import com.babacar.app.entities.AirCraftProsseed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirCraftMapper {
    AircraftResponse toAircraftAlertResponse(AirCraftAlert entity);
    AircraftResponse toAircraftProsseedResponse(AirCraftProsseed aircraftState);
}
