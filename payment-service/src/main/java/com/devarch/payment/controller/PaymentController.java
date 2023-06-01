package com.devarch.payment.controller;

import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.status.PaymentStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/debit")
    public Mono<PaymentResponseDTO> bookFlight(@RequestBody Mono<PaymentRequestDTO> mono){
        return Mono.just(new PaymentResponseDTO("", UUID.randomUUID(), 123d, PaymentStatus.PAYMENT_APPROVED))
                .doOnNext(dto -> System.out.println(dto.bookingId()));
    }

    @GetMapping("/credit")
    public void getAllBookings(@RequestBody Mono<PaymentRequestDTO> mono){
        System.out.println("Creited amount");
    }
}
