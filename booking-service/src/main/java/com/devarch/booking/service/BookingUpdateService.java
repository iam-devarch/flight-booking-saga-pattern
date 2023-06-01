package com.devarch.booking.service;

import com.devarch.booking.repository.FlightBookingRepository;
import com.devarch.dto.OrchestratorResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookingUpdateService {

    public BookingUpdateService(FlightBookingRepository repository) {
        this.repository = repository;
    }

    private final FlightBookingRepository repository;

    public Mono<Void> updateOrder(final OrchestratorResponseDTO responseDTO){
        return this.repository.findById(responseDTO.bookingId())
                .doOnNext(p -> p.setStatus(responseDTO.bookingStatus()))
                .flatMap(this.repository::save)
                .then();
    }

}
