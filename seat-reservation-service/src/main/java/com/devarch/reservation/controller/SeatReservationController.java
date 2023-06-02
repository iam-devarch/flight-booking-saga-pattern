package com.devarch.reservation.controller;

import com.devarch.dto.SeatReservationRequestDTO;
import com.devarch.dto.SeatReservationResponseDTO;
import com.devarch.reservation.service.SeatReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatReservationController {

    private static final Logger logger = LoggerFactory.getLogger(SeatReservationController.class);
    public SeatReservationController(SeatReservationService seatReservationService) {
        this.seatReservationService = seatReservationService;
    }

    private SeatReservationService seatReservationService;

    @PostMapping("/reserve")
    public SeatReservationResponseDTO reserve(@RequestBody SeatReservationRequestDTO requestDTO) {
        logger.debug("Reserving {} seats ", requestDTO.bookedSeats() );
        SeatReservationResponseDTO seatReservationResponseDTO = seatReservationService.reserveSeats(requestDTO);
        logger.debug("Reservation status for passengerId {} is {} ", seatReservationResponseDTO.passengerId(), seatReservationResponseDTO.seatReservationStatus());
        return seatReservationResponseDTO;
    }

    @GetMapping("/revert")
    public void credit(@RequestBody SeatReservationRequestDTO requestDTO) {
        seatReservationService.revertSeats(requestDTO);
        logger.debug("Reverted back seats {}", requestDTO.bookedSeats() );
    }
}
