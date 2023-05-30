package com.devarch.dto;

import lombok.Data;

import java.util.UUID;

public record OrchestratorRequestDTO (String passengerName,
                                      String pnrNumber,
                                      String flightNumber,
                                      UUID bookingId,
                                      Double amount){ }
