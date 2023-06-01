package com.devarch.dto;

import java.util.UUID;

public record PaymentRequestDTO(String passengerName,
                                UUID bookingId,
                                Double amount) {
}