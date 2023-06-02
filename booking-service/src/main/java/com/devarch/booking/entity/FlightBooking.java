package com.devarch.booking.entity;

import com.devarch.status.BookingStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Data
public class FlightBooking {
    @Id
    UUID id;
    Integer passengerId;
    String pnrNumber;
    String flightNumber;
    Double price;
    BookingStatus status;
    Integer bookedSeats;

}
