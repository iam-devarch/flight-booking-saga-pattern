package com.devarch.booking.service;

import com.devarch.booking.entity.FlightBooking;
import com.devarch.dto.BookingRequestDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookingService {

    public Mono<FlightBooking> bookFlight(BookingRequestDTO bookingRequestDTO){
        return Mono.just(new FlightBooking());
    }

}
