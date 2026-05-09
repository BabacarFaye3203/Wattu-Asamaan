package com.babacar.app.service;

import com.babacar.app.dto.AircraftState;
import com.babacar.app.dto.OpenSkyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenSkyService {
    private final RestTemplate restTemplate;

    private final String URL = "https://opensky-network.org/api/states/all";

    public List<AircraftState> fetchAircrafts() {

//        RestTemplate restTemplate = new RestTemplate();
        OpenSkyResponse response = restTemplate.getForObject(URL, OpenSkyResponse.class);
        List<AircraftState> result = new ArrayList<>();

        if (response != null && response.states() != null) {
            for (List<Object> state : response.states()) {

                AircraftState aircraft = new AircraftState(
                        (String) state.get(0),
                        (String) state.get(1),
                        toDouble(state.get(5)),  // longitude
                        toDouble(state.get(6)),  // latitude
                        toDouble(state.get(7)),  // altitude
                        toDouble(state.get(9)),  // velocity
                        (String) state.get(2),
                        System.currentTimeMillis()
                );
                if (aircraft.latitude() == null || aircraft.longitude() == null) continue;

                result.add(aircraft);
            }
        }

        return result;
    }

    private Double toDouble(Object value) {
        if (value == null) return null;
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return null;
    }
}
