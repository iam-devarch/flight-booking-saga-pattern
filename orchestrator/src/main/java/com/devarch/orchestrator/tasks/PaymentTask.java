package com.devarch.orchestrator.tasks;

import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.orchestrator.service.TransactionTask;
import com.devarch.orchestrator.service.TransactionTaskStatus;
import com.devarch.status.PaymentStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PaymentTask implements TransactionTask {

    private final WebClient webClient;
    private final PaymentRequestDTO requestDTO;
    private TransactionTaskStatus taskStatus = TransactionTaskStatus.PENDING;

    public PaymentTask(WebClient webClient, PaymentRequestDTO requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public TransactionTaskStatus getStatus() {
        return this.taskStatus;
    }

    @Override
    public Mono<Boolean> commit() {
        return this.webClient
                .post()
                .uri("/payment/debit")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .map(r -> r.paymentStatus().equals(PaymentStatus.PAYMENT_APPROVED))
                .doOnNext(b -> this.taskStatus = b ?TransactionTaskStatus.COMPLETE : TransactionTaskStatus.FAILED);
    }

    @Override
    public Mono<Boolean> rollback() {
        return this.webClient
                .post()
                .uri("/payment/credit")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }
}
