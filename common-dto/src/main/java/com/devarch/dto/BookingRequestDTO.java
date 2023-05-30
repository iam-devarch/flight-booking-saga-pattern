package com.devarch.dto;

import java.util.UUID;

public record BookingRequestDTO(String passengerName, String pnrNumber, String flightNumber, UUID bookingId) {

    public BookingRequestDTO withBookingId(UUID bookingId){
        return new BookingRequestDTO(passengerName(), pnrNumber(), flightNumber(), bookingId);
    }

}
