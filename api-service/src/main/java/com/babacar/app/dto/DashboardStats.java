package com.babacar.app.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStats {

    private int totalAircrafts;

    private int activeAlerts;

    private double averageSpeed;

    private String mostActiveCountry;
}