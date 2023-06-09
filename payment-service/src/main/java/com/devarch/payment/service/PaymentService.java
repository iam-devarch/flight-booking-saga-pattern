package com.devarch.payment.service;


import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.status.PaymentStatus;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private Map<Integer, Double> passengerAmountBalance;

    @PostConstruct
    private void init(){
        this.passengerAmountBalance = new ConcurrentHashMap<>();
        this.passengerAmountBalance.put(123, 1000d);
        this.passengerAmountBalance.put(456, 1000d);
        this.passengerAmountBalance.put(789, 1000d);
    }

    public PaymentResponseDTO debit(final PaymentRequestDTO requestDTO){
        double balance = this.passengerAmountBalance.getOrDefault(requestDTO.passengerId(), 0d);
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(requestDTO.passengerId(), requestDTO.bookingId(), requestDTO.amount(), PaymentStatus.PAYMENT_REJECTED);
        if(balance >= requestDTO.amount()){
            responseDTO = responseDTO.withPaymentStatus(PaymentStatus.PAYMENT_APPROVED);
            this.passengerAmountBalance.put(requestDTO.passengerId(), balance - requestDTO.amount());
        }
        logger.debug("Available balance after debit is {}", passengerAmountBalance.get(requestDTO.passengerId()));
        return responseDTO;
    }

    public void credit(final PaymentRequestDTO requestDTO){
        this.passengerAmountBalance.computeIfPresent(requestDTO.passengerId(), (k, v) -> Double.sum(v, requestDTO.amount()));
        logger.debug("Available balance after Credit is {}", passengerAmountBalance.get(requestDTO.passengerId()));
    }

}
