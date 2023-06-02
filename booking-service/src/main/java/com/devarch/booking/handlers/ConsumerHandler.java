package com.devarch.booking.handlers;

import com.devarch.booking.service.BookingUpdateService;
import com.devarch.dto.OrchestratorResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class ConsumerHandler {

    public ConsumerHandler(BookingUpdateService bookingUpdateService) {
        this.bookingUpdateService = bookingUpdateService;
    }

    BookingUpdateService bookingUpdateService;

    @Bean
    public Consumer<Flux<OrchestratorResponseDTO>> bookingConsumer() {
        return f -> f
                .doOnNext(c -> System.out.println("Consuming :: " + c))
                .flatMap(responseDTO -> bookingUpdateService.updateOrder(responseDTO))
                .subscribe();
    };

}
