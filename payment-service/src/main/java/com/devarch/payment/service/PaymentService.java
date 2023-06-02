package com.devarch.payment.service;


import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.status.PaymentStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

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
        System.out.printf("Available balance after debit is %f \n", passengerAmountBalance.get(requestDTO.passengerId()));
        return responseDTO;
    }

    public void credit(final PaymentRequestDTO requestDTO){
        this.passengerAmountBalance.computeIfPresent(requestDTO.passengerId(), (k, v) -> Double.sum(v, requestDTO.amount()));
        System.out.printf("Available balance after Credit is %f \n", passengerAmountBalance.get(requestDTO.passengerId()));
    }

}
