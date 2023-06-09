package com.devarch.booking.service;

import com.devarch.booking.entity.FlightBooking;
import com.devarch.booking.repository.FlightBookingRepository;
import com.devarch.dto.BookingRequestDTO;
import com.devarch.dto.BookingResponseDTO;
import com.devarch.dto.OrchestratorRequestDTO;
import com.devarch.status.BookingStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Service
public class BookingService {

    private static final Map<String, Double> BOOKING_PRICE = Map.of(
            "Flt-100", 100d,
            "Flt-500", 500d,
            "Flt-900", 900d
    );

    public BookingService(FlightBookingRepository flightBookingRepository, Sinks.Many<OrchestratorRequestDTO> orchestratorSink) {
        this.flightBookingRepository = flightBookingRepository;
        this.orchestratorSink = orchestratorSink;
    }

    private final FlightBookingRepository flightBookingRepository;

    private final Sinks.Many<OrchestratorRequestDTO> orchestratorSink;

    public Flux<BookingResponseDTO> getAll() {
        return this.flightBookingRepository.findAll()
                .map(this::convertToDTO);
    }

    private BookingResponseDTO convertToDTO(final FlightBooking flightBooking) {

        return new BookingResponseDTO(flightBooking.getId(),
                flightBooking.getPassengerId(), flightBooking.getPnrNumber(),
                flightBooking.getFlightNumber(),
                flightBooking.getPrice(),
                flightBooking.getStatus());
    }

    public Mono<FlightBooking> bookFlight(BookingRequestDTO bookingRequestDTO) {
        return this.flightBookingRepository.save(this.createEntityFor(bookingRequestDTO))
                .doOnNext(e -> this.emitCreatedEvent(bookingRequestDTO.withBookingId(e.getId())));
    }

    private FlightBooking createEntityFor(BookingRequestDTO bookingRequestDTO) {
        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setId(bookingRequestDTO.bookingId());
        flightBooking.setFlightNumber(bookingRequestDTO.flightNumber());
        flightBooking.setPassengerId(bookingRequestDTO.passengerId());
        flightBooking.setPnrNumber(bookingRequestDTO.pnrNumber());
        flightBooking.setPrice(BOOKING_PRICE.get(bookingRequestDTO.flightNumber()));
        flightBooking.setStatus(BookingStatus.BOOKING_CREATED);
        flightBooking.setBookedSeats(bookingRequestDTO.bookedSeats());

        return flightBooking;
    }

    private void emitCreatedEvent(BookingRequestDTO bookingRequestDTO) {
        this.orchestratorSink.tryEmitNext(this.getOrchestratorRequestDTO(bookingRequestDTO));
    }

    public OrchestratorRequestDTO getOrchestratorRequestDTO(BookingRequestDTO bookingRequestDTO) {
        Double amount = BOOKING_PRICE.get(bookingRequestDTO.flightNumber());
        return new OrchestratorRequestDTO(bookingRequestDTO.bookingId(),
                bookingRequestDTO.passengerId(),
                bookingRequestDTO.pnrNumber(),
                bookingRequestDTO.flightNumber(),
                amount,
                bookingRequestDTO.bookedSeats());
    }

}
