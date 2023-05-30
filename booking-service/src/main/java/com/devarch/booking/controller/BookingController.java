package com.devarch.booking.controller;

import com.devarch.booking.entity.FlightBooking;
import com.devarch.booking.service.BookingService;
import com.devarch.dto.BookingRequestDTO;
import com.devarch.dto.BookingResponseDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("booking")
public class BookingController {

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private BookingService bookingService;

    @PostMapping("/confirm")
    public Mono<FlightBooking> bookFlight(@RequestBody Mono<BookingRequestDTO> mono){
        return mono.flatMap(this.bookingService::bookFlight);
    }

    @GetMapping("/showAll")
    public Flux<BookingResponseDTO> getAllBookings(){
        return this.bookingService.getAll();
    }
}
