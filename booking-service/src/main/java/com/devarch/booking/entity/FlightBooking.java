package com.devarch.booking.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Data
public class FlightBooking {
    @Id
    UUID id;
    String passengerName;
    String pnrNumber;
    String flightNumber;
    String status;

}
