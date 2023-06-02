package com.devarch.dto;

import com.devarch.status.SeatReservationStatus;

import java.util.UUID;

public record SeatReservationResponseDTO(Integer passengerId,
                                         String flightNumber,
                                         UUID bookingId,
                                         Integer totalSeats,
                                         SeatReservationStatus seatReservationStatus) {

    public SeatReservationResponseDTO withPaymentStatus(SeatReservationStatus seatReservationStatus){
        return new SeatReservationResponseDTO(passengerId(), flightNumber(), bookingId(), totalSeats(), seatReservationStatus);
    }

}