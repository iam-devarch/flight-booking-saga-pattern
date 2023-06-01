package com.devarch.dto;

import com.devarch.status.BookingStatus;

import java.util.UUID;

public record OrchestratorResponseDTO(UUID bookingId,
                                      Integer passengerId,
                                      String pnrNumber,
                                      String flightNumber,
                                      Double amount,
                                      BookingStatus bookingStatus){ }
