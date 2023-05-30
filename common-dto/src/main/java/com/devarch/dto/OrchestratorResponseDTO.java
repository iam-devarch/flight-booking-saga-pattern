package com.devarch.dto;

import com.devarch.enums.BookingStatus;

import java.util.UUID;

public record OrchestratorResponseDTO(UUID bookingId, BookingStatus status){ }
