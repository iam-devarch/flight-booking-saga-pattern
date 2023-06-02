package com.devarch.orchestrator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("service.endpoints")
public record OrchestratorProperties (String seatReservation, String payment) {}