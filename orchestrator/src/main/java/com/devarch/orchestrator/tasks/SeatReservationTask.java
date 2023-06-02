package com.devarch.orchestrator.tasks;

import com.devarch.dto.PaymentRequestDTO;
import com.devarch.dto.PaymentResponseDTO;
import com.devarch.dto.SeatReservationRequestDTO;
import com.devarch.dto.SeatReservationResponseDTO;
import com.devarch.orchestrator.service.TransactionTask;
import com.devarch.orchestrator.service.TransactionTaskStatus;
import com.devarch.status.PaymentStatus;
import com.devarch.status.SeatReservationStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SeatReservationTask implements TransactionTask {

    private final WebClient webClient;
    private final SeatReservationRequestDTO requestDTO;
    private TransactionTaskStatus taskStatus = TransactionTaskStatus.PENDING;

    public SeatReservationTask(WebClient webClient, SeatReservationRequestDTO requestDTO) {
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
                .uri("/seats/reserve")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(SeatReservationResponseDTO.class)
                .map(r -> r.seatReservationStatus().equals(SeatReservationStatus.RESERVATION_CONFIRMED))
                .doOnNext(b -> this.taskStatus = b ?TransactionTaskStatus.COMPLETE : TransactionTaskStatus.FAILED);
    }

    @Override
    public Mono<Boolean> rollback() {
        return this.webClient
                .post()
                .uri("/seats/revert")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }
}
