package com.devarch.dto;

import java.util.UUID;

public record SeatReservationRequestDTO(Integer passengerId,
                                        String flightNumber,
                                        UUID bookingId,
                                        Integer bookedSeats) {
}