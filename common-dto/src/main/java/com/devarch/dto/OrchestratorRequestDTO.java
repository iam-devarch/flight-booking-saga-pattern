package com.devarch.dto;

import java.util.UUID;

public record OrchestratorRequestDTO (UUID bookingId,
                                      Integer passengerId,
                                      String pnrNumber,
                                      String flightNumber,
                                      Double amount){ }
