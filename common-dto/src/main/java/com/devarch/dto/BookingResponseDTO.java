package com.devarch.dto;

import com.devarch.enums.BookingStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class BookingResponseDTO {

    private UUID bookingId;
    private String passengerName;
    private String pnrNumber;
    private String flightNumber;
    private Double amount;
    private BookingStatus status;

}
