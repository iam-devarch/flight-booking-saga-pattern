package com.devarch.payment.controller;

import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private PaymentService paymentService;

    @PostMapping("/debit")
    public PaymentResponseDTO debit(@RequestBody PaymentRequestDTO requestDTO) {
        logger.debug("Debiting amount {} ", requestDTO.amount() );
        PaymentResponseDTO paymentResponseDTO = paymentService.debit(requestDTO);
        logger.debug("Debit status is {} ", paymentResponseDTO.paymentStatus());
        return paymentResponseDTO;
    }

    @GetMapping("/credit")
    public void credit(@RequestBody PaymentRequestDTO requestDTO) {
        paymentService.credit(requestDTO);
        logger.debug("Credited back amount {}", requestDTO.amount() );
    }
}
