package com.devarch.dto;

import com.devarch.status.PaymentStatus;

import java.util.UUID;

    public record PaymentResponseDTO(String passengerName,
                                     UUID bookingId,
                                     Double amount,
                                     PaymentStatus paymentStatus) {
    }