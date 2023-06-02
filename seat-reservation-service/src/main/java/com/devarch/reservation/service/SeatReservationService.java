package com.devarch.reservation.service;


import com.devarch.dto.SeatReservationRequestDTO;
import com.devarch.dto.SeatReservationResponseDTO;
import com.devarch.status.SeatReservationStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SeatReservationService {

    private Map<String, Integer> flightSeatsAvailability;

    @PostConstruct
    private void init(){
        this.flightSeatsAvailability = new ConcurrentHashMap<>();
        this.flightSeatsAvailability.put("Flt-100", 20);
        this.flightSeatsAvailability.put("Flt-500", 30);
        this.flightSeatsAvailability.put("Flt-900", 50);
    }

    public SeatReservationResponseDTO reserveSeats(final SeatReservationRequestDTO requestDTO){
        int availableSeats = this.flightSeatsAvailability.getOrDefault(requestDTO.flightNumber(), 0);
        SeatReservationResponseDTO responseDTO = new SeatReservationResponseDTO(requestDTO.passengerId(),requestDTO.flightNumber(),
                requestDTO.bookingId(),requestDTO.bookedSeats(), SeatReservationStatus.RESERVATION_REJECTED);
        if(availableSeats >= requestDTO.bookedSeats()){
            responseDTO = responseDTO.withPaymentStatus(SeatReservationStatus.RESERVATION_CONFIRMED);
            this.flightSeatsAvailability.put(requestDTO.flightNumber(), availableSeats - requestDTO.bookedSeats());
        }
        return responseDTO;
    }

    public void revertSeats(final SeatReservationRequestDTO requestDTO){
        this.flightSeatsAvailability.computeIfPresent(requestDTO.flightNumber(), (k, v) -> Integer.sum(v, requestDTO.bookedSeats()));
    }

}
