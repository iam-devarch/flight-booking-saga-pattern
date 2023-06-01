package com.devarch.dto;

import java.util.UUID;

public record OrchestratorRequestDTO (UUID bookingId,
                                      String passengerName,
                                      String pnrNumber,
                                      String flightNumber,
                                      Double amount){ }
