package com.devarch.dto;

import com.devarch.status.PaymentStatus;

import java.util.UUID;

    public record PaymentResponseDTO(Integer passengerId,
                                     UUID bookingId,
                                     Double amount,
                                     PaymentStatus paymentStatus) {

        public PaymentResponseDTO withPaymentStatus(PaymentStatus paymentStatus){
            return new PaymentResponseDTO(passengerId(), bookingId(), amount(), paymentStatus);
        }

    }