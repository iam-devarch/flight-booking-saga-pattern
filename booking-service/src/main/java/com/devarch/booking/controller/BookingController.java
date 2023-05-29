package com.devarch.booking.controller;

import com.devarch.booking.entity.FlightBooking;
import com.devarch.booking.service.BookingService;
import com.devarch.dto.BookingRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("booking")
public class BookingController {

    public BookingController(BookingService service) {
        this.service = service;
    }

    private BookingService service;

    @PostMapping("/confirm")
    public Mono<FlightBooking> bookFlight(@RequestBody Mono<BookingRequestDTO> mono){
        return mono.flatMap(this.service::bookFlight);
    }


}
