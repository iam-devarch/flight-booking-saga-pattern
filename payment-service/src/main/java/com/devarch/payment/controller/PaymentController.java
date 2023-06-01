package com.devarch.payment.controller;

import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private PaymentService paymentService;

    @PostMapping("/debit")
    public PaymentResponseDTO debit(@RequestBody PaymentRequestDTO requestDTO) {
        System.out.printf("Debiting amount %f \n", requestDTO.amount() );
        PaymentResponseDTO paymentResponseDTO = paymentService.debit(requestDTO);
        System.out.printf("Debit status is %s \n", paymentResponseDTO.paymentStatus());
        return paymentResponseDTO;
    }

    @GetMapping("/credit")
    public void credit(@RequestBody PaymentRequestDTO requestDTO) {
        paymentService.credit(requestDTO);
        System.out.printf("Credited back amount %f", requestDTO.amount() );
    }
}
