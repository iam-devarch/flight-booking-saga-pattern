package com.devarch.reservation.controller;

import com.devarch.dto.SeatReservationRequestDTO;
import com.devarch.dto.SeatReservationResponseDTO;
import com.devarch.reservation.service.SeatReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatReservationController {

    public SeatReservationController(SeatReservationService seatReservationService) {
        this.seatReservationService = seatReservationService;
    }

    private SeatReservationService seatReservationService;

    @PostMapping("/reserve")
    public SeatReservationResponseDTO reserve(@RequestBody SeatReservationRequestDTO requestDTO) {
        System.out.printf("Reserving %d seats \n", requestDTO.bookedSeats() );
        SeatReservationResponseDTO seatReservationResponseDTO = seatReservationService.reserveSeats(requestDTO);
        System.out.printf("Reservation status for passengerId %s is %s \n", seatReservationResponseDTO.passengerId(), seatReservationResponseDTO.seatReservationStatus());
        return seatReservationResponseDTO;
    }

    @GetMapping("/revert")
    public void credit(@RequestBody SeatReservationRequestDTO requestDTO) {
        seatReservationService.revertSeats(requestDTO);
        System.out.printf("Reverted back seats %d", requestDTO.bookedSeats() );
    }
}
