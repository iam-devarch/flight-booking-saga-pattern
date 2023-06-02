package com.devarch.dto;

import java.util.UUID;

public record PaymentRequestDTO(Integer passengerId,
                                UUID bookingId,
                                Double amount) {
}