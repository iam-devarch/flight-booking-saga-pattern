package com.devarch.dto;

import java.util.UUID;

public record BookingRequestDTO(Integer passengerId, String pnrNumber, String flightNumber, UUID bookingId, Integer bookedSeats) {

    public BookingRequestDTO withBookingId(UUID bookingId){
        return new BookingRequestDTO(passengerId(), pnrNumber(), flightNumber(), bookingId, bookedSeats());
    }

}
