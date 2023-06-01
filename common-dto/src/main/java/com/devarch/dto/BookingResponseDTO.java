package com.devarch.dto;

import com.devarch.status.BookingStatus;

import java.util.UUID;

public record BookingResponseDTO(UUID bookingId,
        String passengerName,
        String pnrNumber,
        String flightNumber,
        Double amount,
        BookingStatus status) {}
