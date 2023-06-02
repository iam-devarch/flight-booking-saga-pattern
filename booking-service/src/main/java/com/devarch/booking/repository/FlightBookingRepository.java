package com.devarch.booking.repository;

import com.devarch.booking.entity.FlightBooking;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightBookingRepository extends ReactiveCrudRepository<FlightBooking, UUID> {
}