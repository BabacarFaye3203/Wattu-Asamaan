package com.babacar.app.dto;

import java.util.List;

public record OpenSkyResponse(
        List<List<Object>> states
) {}