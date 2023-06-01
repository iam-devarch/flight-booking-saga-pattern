package com.devarch.orchestrator.service;


import com.devarch.dto.OrchestratorRequestDTO;
import com.devarch.dto.OrchestratorResponseDTO;
import com.devarch.dto.PaymentRequestDTO;
import com.devarch.orchestrator.tasks.PaymentTask;
import com.devarch.status.BookingStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookingOrchestratorService {


    private WebClient paymentClient;


    private WebClient seatReservationClient;

    public BookingOrchestratorService(@Qualifier("paymentClient") WebClient paymentClient, @Qualifier("seatReservationClient") WebClient seatReservationClient) {
        this.paymentClient = paymentClient;
        this.seatReservationClient = seatReservationClient;
    }

    public Mono<OrchestratorResponseDTO> bookFlight(final OrchestratorRequestDTO requestDTO) {
        BookingTransaction bookingTransaction = getTransactionTasks(requestDTO);
        return Flux.fromStream(() -> bookingTransaction.tasks().stream())
                .flatMap(TransactionTask::commit)
                .handle((isSuccess, synchronousSink) -> {
                    if (isSuccess) {
                        synchronousSink.next(true);
                    } else {
                        synchronousSink.error(new RuntimeException("Flight Booking Failed!"));
                    }
                })
                .then(Mono.fromCallable(() -> getResponse(requestDTO, BookingStatus.BOOKING_COMPLETED)))
                .onErrorResume(ex -> this.revertBooking(bookingTransaction, requestDTO));
    }

    private Mono<OrchestratorResponseDTO> revertBooking(final BookingTransaction transaction, final OrchestratorRequestDTO requestDTO) {
        return Flux.fromStream(() -> transaction.tasks().stream())
                .filter(task -> task.getStatus().equals(TransactionTaskStatus.COMPLETE))
                .flatMap(TransactionTask::rollback)
                .retry(3)
                .then(Mono.just(this.getResponse(requestDTO, BookingStatus.BOOKING_CANCELLED)));
    }


    private OrchestratorResponseDTO getResponse(OrchestratorRequestDTO requestDTO, BookingStatus bookingStatus) {
        return new OrchestratorResponseDTO(requestDTO.bookingId(),
                requestDTO.passengerName(),
                requestDTO.pnrNumber(),
                requestDTO.flightNumber(),
                requestDTO.amount(),
                bookingStatus);
    }

    private BookingTransaction getTransactionTasks(OrchestratorRequestDTO requestDTO) {
        TransactionTask paymentTask = new PaymentTask(paymentClient, getPaymentRequestDTO(requestDTO));
        return new BookingTransaction(List.of(paymentTask));
    }

    private PaymentRequestDTO getPaymentRequestDTO(OrchestratorRequestDTO requestDTO) {
        return new PaymentRequestDTO(requestDTO.passengerName(), requestDTO.bookingId(), requestDTO.amount());

    }

}
